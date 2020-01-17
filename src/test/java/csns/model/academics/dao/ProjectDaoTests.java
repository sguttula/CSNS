package csns.model.academics.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.academics.Department;
import csns.model.academics.Project;

@Test(groups = "ProjectDaoTests", dependsOnGroups = "DepartmentDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class ProjectDaoTests extends
    AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    ProjectDao projectDao;

    @Test
    public void getProjects()
    {

        Department cs = departmentDao.getDepartment( "cs" );
        List<Project> projects = projectDao.getProjects( cs, 2013 );
        assert projects.size() >= 1;

        Project project = projects.get( 0 );
        assert project.getAdvisors().get( 0 ).getUsername().equals( "cysun" );
        assert project.getStudents().size() == 2;
    }

}
