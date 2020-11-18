package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import csns.model.core.Subscription;
import csns.model.core.User;
import csns.model.core.dao.SubscriptionDao;
import csns.model.core.dao.UserDao;
import csns.model.forum.Forum;
import csns.model.forum.Topic;
import csns.model.forum.dao.ForumDao;
import csns.model.forum.dao.PostDao;
import csns.model.forum.dao.TopicDao;
import csns.security.SecurityUtils;

@Controller
public class ForumTopicController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ForumDao forumDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private SubscriptionDao subscriptionDao;

    private static final Logger logger = LoggerFactory
        .getLogger( ForumTopicController.class );

    @RequestMapping("/department/{dept}/forum/topic/view")
    public String view( @RequestParam Long id, ModelMap models )
    {
        Topic topic = topicDao.getTopic( id );
        topic.incrementNumOfViews();
        topicDao.saveTopic( topic );

        if( SecurityUtils.isAuthenticated() )
        {
            User user = userDao.getUser( SecurityUtils.getUser().getId() );

            Subscription subscription = subscriptionDao.getSubscription( topic,
                user );
            if( subscription != null && subscription.isNotificationSent() )
            {
                subscription.setNotificationSent( false );
                subscription = subscriptionDao.saveSubscription( subscription );
            }

            models.put( "user", user );
            models.put( "isModerator", topic.getForum().isModerator( user ) );
            models.put( "subscription", subscription );
        }

        models.put( "topic", topic );
        return "forum/topic/view";
    }

    @RequestMapping("/department/{dept}/forum/topic/pin")
    @ResponseBody
    public void pin( @RequestParam Long id, ModelMap models )
    {
        Topic topic = topicDao.getTopic( id );
        topic.setPinned( true );
        topicDao.saveTopic( topic );

        logger.info( SecurityUtils.getUser().getUsername() + " pinned topic "
            + topic.getId() );
    }

    @RequestMapping("/department/{dept}/forum/topic/unpin")
    @ResponseBody
    public void unpin( @RequestParam Long id, ModelMap models )
    {
        Topic topic = topicDao.getTopic( id );
        topic.setPinned( false );
        topicDao.saveTopic( topic );

        logger.info( SecurityUtils.getUser().getUsername() + " unpinned topic "
            + topic.getId() );
    }

    @RequestMapping("/department/{dept}/forum/topic/search")
    public String search( @RequestParam Long forumId, @RequestParam String text,
        ModelMap models )
    {
        Forum forum = forumDao.getForum( forumId );
        models.put( "forum", forum );
        models.put( "posts", postDao.searchPosts( forum, text, 40 ) );
        return "forum/topic/search";
    }

    @RequestMapping("/department/{dept}/forum/topic/delete")
    public String delete( @PathVariable String dept, @RequestParam Long id )
    {
        Topic topic = topicDao.getTopic( id );
        topic.setDeleted( true );
        topicDao.saveTopic( topic );

        logger.info( SecurityUtils.getUser().getUsername() + " deleted topic "
            + topic.getId() );

        return "redirect:/department/" + dept + "/forum/view?id="
            + topic.getForum().getId();
    }

}
