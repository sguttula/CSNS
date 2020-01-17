package csns.model.academics.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.academics.Term;
import csns.model.core.User;
import csns.model.core.dao.UserDao;

@Test(groups = "TermDaoTests", dependsOnGroups = "UserDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class TermDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    @Autowired
    TermDao termDao;

    @Test
    public void getTermsByInstructor()
    {
        User cysun = userDao.getUserByUsername( "cysun" );
        List<Term> terms = termDao.getTermsByInstructor( cysun );
        assert terms.size() == 2;
        assert terms.get( 1 ).getCode() == 1109;
    }

    @Test
    public void getTermsByStudent()
    {
        User jdoe1 = userDao.getUserByUsername( "jdoe1" );
        List<Term> terms = termDao.getTermsByStudent( jdoe1 );
        assert terms.size() == 2;
        assert terms.get( 1 ).getCode() == 1109;
    }

}
