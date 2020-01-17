package csns.model.forum.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test(groups = "TopicDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class TopicDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    TopicDao topicDao;

    @Test
    public void getTopic()
    {
        assert topicDao.getTopic( 1000800L ) != null;
    }

}
