package csns.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.academics.Department;
import csns.model.academics.dao.DepartmentDao;
import csns.model.core.Subscription;
import csns.model.core.User;
import csns.model.core.dao.SubscriptionDao;
import csns.model.core.dao.UserDao;
import csns.model.forum.Forum;
import csns.model.forum.dao.ForumDao;
import csns.security.SecurityUtils;

@Controller
public class ForumController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ForumDao forumDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private SubscriptionDao subscriptionDao;

    private static final Logger logger = LoggerFactory
        .getLogger( ForumController.class );

    @RequestMapping("/department/{dept}/forum/list")
    public String list( @PathVariable String dept,
        @RequestParam(required = false ) Boolean showAll, HttpSession session,
        ModelMap models)
    {
        Department department = departmentDao.getDepartment( dept );
        models.put( "department", department );
        models.put( "systemForums", forumDao.getSystemForums() );

        User user = SecurityUtils.getUser();
        List<Forum> departmentForums = new ArrayList<Forum>();
        for( Forum departmentForum : department.getForums() )
            if( !departmentForum.isMembersOnly()
                || departmentForum.isMember( user ) )
                departmentForums.add( departmentForum );
        models.put( "departmentForums", departmentForums );

        if( showAll != null )
        {
            if( showAll )
                session.setAttribute( "showAll", true );
            else
                session.removeAttribute( "showAll" );
        }

        List<Forum> courseForums = new ArrayList<Forum>();
        if( SecurityUtils.isAnonymous()
            || session.getAttribute( "showAll" ) != null )
        {
            courseForums = forumDao.getCourseForums( department );
        }
        else
        {
            List<Subscription> subscriptions = subscriptionDao
                .getSubscriptions( SecurityUtils.getUser(), Forum.class );
            for( Subscription subscription : subscriptions )
            {
                Forum forum = (Forum) subscription.getSubscribable();
                if( forum.getCourse() != null ) courseForums.add( forum );
            }
        }
        models.put( "courseForums", courseForums );

        return "forum/list";
    }

    @RequestMapping("/department/{dept}/forum/view")
    public String view( @PathVariable String dept, @RequestParam Long id,
        ModelMap models )
    {
        Forum forum = forumDao.getForum( id );
        if( SecurityUtils.isAuthenticated() )
        {
            User user = userDao.getUser( SecurityUtils.getUser().getId() );
            models.put( "isModerator", forum.isModerator( user ) );
            models.put( "subscription",
                subscriptionDao.getSubscription( forum, user ) );
        }

        models.put( "forum", forum );
        models.put( "department", departmentDao.getDepartment( dept ) );

        return "forum/view";
    }

    @RequestMapping("/department/{dept}/forum/delete")
    public String delete( @RequestParam Long id )
    {
        Forum forum = forumDao.getForum( id );
        if( forum.getDepartment() != null )
        {
            forum.setDepartment( null );
            forum.setHidden( true );
            forum = forumDao.saveForum( forum );
        }

        logger.info( SecurityUtils.getUser().getUsername() + " deleted forum "
            + forum.getId() );

        return "redirect:list";
    }

}
