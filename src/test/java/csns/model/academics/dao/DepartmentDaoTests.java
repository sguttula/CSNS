package csns.model.academics.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.academics.Department;

@Test(groups = "DepartmentDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class DepartmentDaoTests extends
    AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    DepartmentDao departmentDao;

    @Test
    public void getDepartment()
    {
        assert departmentDao.getDepartment( "cs" ) != null;
    }

    @Test
    public void getDepartments()
    {
        assert departmentDao.getDepartments().size() == 2;
    }

    @Test(dependsOnMethods = "getDepartment")
    public void getDepartmentAdministrators()
    {
        Department cs = departmentDao.getDepartment( "cs" );

        assert cs.getAdministrators().size() == 2;
        assert cs.getAdministrators().get( 0 ).getUsername().equals( "rpamula" );
    }

}
