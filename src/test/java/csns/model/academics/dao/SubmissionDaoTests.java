package csns.model.academics.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.academics.Assignment;
import csns.model.academics.Section;
import csns.model.core.User;
import csns.model.core.dao.UserDao;

@Test(groups = "SubmissionDaoTest", dependsOnGroups = { "SectionDaoTests",
    "AssignmentDaoTests" })
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class SubmissionDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    @Autowired
    SectionDao sectionDao;

    @Autowired
    AssignmentDao assignmentDao;

    @Autowired
    SubmissionDao submissionDao;

    @Test
    public void getSubmission()
    {
        User student = userDao.getUserByUsername( "jdoe1" );
        Assignment assignment = assignmentDao.getAssignment( 1000500L );

        assert submissionDao.getSubmission( student, assignment ) != null;
    }

    @Test
    public void getSubmissions()
    {
        User student = userDao.getUserByUsername( "jdoe1" );
        Section section = sectionDao.getSection( 1000300L );

        assert submissionDao.getSubmissions( student, section ).size() == 1;
    }

}
