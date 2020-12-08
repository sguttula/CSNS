package csns.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import csns.model.core.User;
import csns.model.core.dao.SubscriptionDao;
import csns.model.forum.Forum;
import csns.model.forum.Post;
import csns.model.forum.Topic;
import csns.model.forum.dao.ForumDao;
import csns.model.forum.dao.TopicDao;
import csns.model.wiki.Page;
import csns.model.wiki.Revision;
import csns.model.wiki.dao.PageDao;
import csns.model.wiki.dao.RevisionDao;
import csns.security.SecurityUtils;
import csns.util.FileIO;
import csns.util.NotificationService;
import csns.web.validator.MessageValidator;
import freemarker.template.TemplateException;

@Controller
@SessionAttributes({ "revision", "post" })
public class WikiControllerS {

    @Autowired
    private PageDao pageDao;

    @Autowired
    private RevisionDao revisionDao;

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Autowired
    private ForumDao forumDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private FileIO fileIO;

    @Autowired
    private MessageValidator messageValidator;

    @Autowired
    private NotificationService notificationService;

    private static final Logger logger = LoggerFactory
        .getLogger( WikiControllerS.class );

    @RequestMapping(value = "/wiki/edit", method = RequestMethod.GET)
    public String edit( @RequestParam String path, ModelMap models,
        HttpSession session )
    {
        Revision revision = revisionDao.getRevision( path );

        if( revision != null )
        {
            Page page = revision.getPage();

            if( StringUtils.hasText( page.getPassword() )
                && session.getAttribute( path ) == null )
                return "wiki/password";

            User user = SecurityUtils.getUser();
            String dept = WikiController.getDept( path );
            if( page.isLocked()
                && !page.getOwner().getId().equals( user.getId() )
                && (dept == null ? !user.isSysadmin() : !user.isAdmin( dept )) )
            {
                models.put( "message", "error.wiki.not.owner" );
                return "error";
            }
        }

        revision = revision == null ? new Revision( new Page( path ) )
            : revision.clone();
        models.put( "revision", revision );
        return "wiki/edit";
    }

    @RequestMapping(value = "/wiki/edit", method = RequestMethod.POST)
    public String edit( @ModelAttribute Revision revision, HttpSession session,
        BindingResult result, SessionStatus sessionStatus )
        throws IOException, TemplateException
    {
        // NOTE In theory it is possible to craft a POST request to bypass both
        // password and locked, but it is unlikely, and considering this is wiki
        // we are taking about here, I'm choosing simplicity over security.

        messageValidator.validate( revision, result );
        if( result.hasErrors() ) return "wiki/edit";

        User user = SecurityUtils.getUser();
        revision.setAuthor( user );
        revision.setDate( new Date() );
        Page page = revision.getPage();
        if( page.getId() == null ) page.setOwner( user );
        page = revisionDao.saveRevision( revision ).getPage();
        sessionStatus.setComplete();

        logger.info( "Wiki page " + page.getPath() + " edited by "
            + user.getUsername() );

        subscriptionDao.subscribe( page, user );

        String subject = "New Revision of Wiki Page " + page.getPath();
        String fTemplate = "notification.new.wiki.revision.txt";
        Map<String, Object> fModels = new HashMap<String, Object>();
        fModels.put( "page", page );
        fModels.put( "author", user );
        notificationService.notifiy( page, subject, fTemplate, fModels, true );

        return "redirect:" + page.getPath();
    }

    @RequestMapping(value = "/wiki/discuss", method = RequestMethod.GET)
    public String discuss( @RequestParam Long pageId, ModelMap models )
    {
        Forum forum = forumDao.getForum( "Wiki Discussion" );
        models.put( "post", new Post( new Topic( forum ) ) );
        models.put( "page", pageDao.getPage( pageId ) );
        return "wiki/discuss";
    }

    @RequestMapping(value = "/wiki/discuss", method = RequestMethod.POST)
    public String discuss( @ModelAttribute Post post, @RequestParam Long pageId,
        @RequestParam(value = "file",
            required = false) MultipartFile[] uploadedFiles,
        BindingResult result, SessionStatus sessionStatus )
        throws IOException, TemplateException
    {
        messageValidator.validate( post, result );
        if( result.hasErrors() ) return "wiki/discuss";

        User user = SecurityUtils.getUser();
        if( uploadedFiles != null ) post.getAttachments()
            .addAll( fileIO.save( uploadedFiles, user, true ) );

        post.setAuthor( user );
        post.setDate( new Date() );
        Topic topic = post.getTopic();
        topic.addPost( post );
        Forum forum = topic.getForum();
        forum.incrementNumOfTopics();
        forum.incrementNumOfPosts();
        forum.setLastPost( post );
        topic = topicDao.saveTopic( topic );
        sessionStatus.setComplete();

        Page page = pageDao.getPage( pageId );
        page.getDiscussions().add( topic );
        pageDao.savePage( page );

        subscriptionDao.subscribe( page, user );

        String subject = "New Discussion of Wiki Page " + page.getPath();
        String fTemplate = "notification.new.wiki.discussion.txt";
        Map<String, Object> fModels = new HashMap<String, Object>();
        fModels.put( "page", page );
        fModels.put( "author", user );
        fModels.put( "topic", topic );
        notificationService.notifiy( page, subject, fTemplate, fModels, false );

        return "redirect:/wiki/discussions?id=" + page.getId();
    }

}
