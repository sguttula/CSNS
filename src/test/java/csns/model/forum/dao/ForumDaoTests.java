package csns.model.forum.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.core.dao.UserDao;

@Test(groups = "ForumDaoTests", dependsOnGroups = "UserDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class ForumDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    @Autowired
    ForumDao forumDao;

    @Test
    public void getForum()
    {
        assert forumDao.getForum( 1000700L ) != null;
    }

}
