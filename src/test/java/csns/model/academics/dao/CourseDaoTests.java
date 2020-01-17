package csns.model.academics.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.academics.Course;

@Test(groups = "CourseDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class CourseDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    CourseDao courseDao;

    @Test
    public void getCourse()
    {
        assert courseDao.getCourse( "CS520" ) != null;
    }

    @Test
    public void searchCourses()
    {
        List<Course> courses = courseDao.searchCourses( "programming", true,
            -1 );
        assert courses.size() == 2;
    }

}
