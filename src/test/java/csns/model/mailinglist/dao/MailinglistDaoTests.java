package csns.model.mailinglist.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test(groups = "MailinglistDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class MailinglistDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    MailinglistDao mailinglistDao;

    @Test
    public void getMailinglist()
    {
        assert mailinglistDao.getMailinglist( 1001101L ) != null;
    }

}
