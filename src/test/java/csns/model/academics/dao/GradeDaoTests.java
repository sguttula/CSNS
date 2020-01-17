package csns.model.academics.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test(groups = "GradeDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class GradeDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    GradeDao gradeDao;

    @Test
    public void getGrade()
    {
        assert gradeDao.getGrade( "A" ).getValue().equals( 4.0 );
    }

}
