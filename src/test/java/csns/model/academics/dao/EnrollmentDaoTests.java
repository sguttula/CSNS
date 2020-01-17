package csns.model.academics.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.academics.Enrollment;
import csns.model.core.User;
import csns.model.core.dao.UserDao;

@Test(groups = "EnrollmentDaoTests", dependsOnGroups = "UserDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class EnrollmentDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    @Autowired
    EnrollmentDao enrollmentDao;

    @Test
    public void getEnrollments()
    {
        User jdoe1 = userDao.getUserByUsername( "jdoe1" );
        List<Enrollment> enrollments = enrollmentDao.getEnrollments( jdoe1 );
        assert enrollments.size() == 2;
        assert enrollments.get( 1 ).getSection().getTerm().getCode() == 1109;
    }

}
