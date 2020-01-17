package csns.model.academics.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.academics.Assignment;
import csns.model.academics.Submission;

@Test(groups = "AssignmentDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class AssignmentDaoTests extends
    AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    AssignmentDao assignmentDao;

    @Test
    public void getAssignment()
    {
        assert assignmentDao.getAssignment( 1000500L ) != null;
    }

    /**
     * To test lazy loading of collections like submissions, the test class must
     * extend AbstractTransactionalTestNGSpringContextTests instead of
     * AbstractTestNGSpringContextTests so Spring will run each test method in a
     * transaction and keep the entity manager open until the method finishes.
     */
    @Test(dependsOnMethods = "getAssignment")
    public void loadSubmissions()
    {
        Assignment assignment = assignmentDao.getAssignment( 1000500L );
        List<Submission> submissions = assignment.getSubmissions();

        assert submissions.size() == 2;
    }

}
