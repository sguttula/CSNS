package csns.model.core.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.core.Subscription;
import csns.model.core.User;
import csns.model.forum.Forum;
import csns.model.forum.Topic;
import csns.model.forum.dao.ForumDao;
import csns.model.forum.dao.TopicDao;

@Test(groups = "SubscriptionDaoTests", dependsOnGroups = { "UserDaoTests",
    "ForumDaoTests", "TopicDaoTests" })
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class SubscriptionDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    @Autowired
    ForumDao forumDao;

    @Autowired
    TopicDao topicDao;

    @Autowired
    SubscriptionDao subscriptionDao;

    @Test
    public void getSubscription()
    {
        Forum forum = forumDao.getForum( 3000L );
        User subscriber = userDao.getUser( 1000001L );
        assert subscriptionDao.getSubscription( forum, subscriber ) != null;
    }

    @Test
    public void getForumSubscribers()
    {
        Forum forum = forumDao.getForum( 3000L );
        List<Subscription> subscriptions = subscriptionDao.getSubscriptions( forum );
        assert subscriptions.size() == 1;
        assert subscriptions.get( 0 )
            .getSubscriber()
            .getUsername()
            .equals( "cysun" );
    }

    @Test
    public void getUserSubscriptions()
    {
        User user = userDao.getUser( 1000001L );
        assert subscriptionDao.getSubscriptions( user, Topic.class ).size() == 1;
    }

}
