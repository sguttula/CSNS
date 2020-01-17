package csns.model.mailinglist.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.mailinglist.Mailinglist;
import csns.model.mailinglist.Message;

@Test(groups = "MessageDaoTests", dependsOnGroups = "MailinglistDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class MessageDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    MailinglistDao mailinglistDao;

    @Autowired
    MessageDao messageDao;

    @Test
    public void searchMessages()
    {
        Mailinglist mailinglist = mailinglistDao.getMailinglist( 1001101L );
        List<Message> messages = messageDao.searchMessages( mailinglist,
            "welcome", 10 );

        assert messages.get( 0 ).getAuthor().getUsername().equals( "rpamula" );
    }

}
