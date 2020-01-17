package csns.model.assessment.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.academics.Department;
import csns.model.academics.dao.DepartmentDao;
import csns.model.assessment.Rubric;
import csns.model.core.User;
import csns.model.core.dao.UserDao;

@Test(groups = "RubricDaoTests", dependsOnGroups = { "UserDaoTests",
    "DepartmentDaoTests" })
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class RubricDaoTests extends
    AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RubricDao rubricDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void getRubric()
    {
        Rubric rubric = rubricDao.getRubric( 1001400L );
        assert rubric != null;
    }

    @Test(dependsOnMethods = "getRubric")
    public void getIndicators()
    {
        Rubric rubric = rubricDao.getRubric( 1001400L );
        assert rubric.getIndicators().size() == 2;
        assert rubric.getIndicators().get( 0 ).getCriteria().size() == 3;
    }

    @Test
    public void getDepartmentRubrics()
    {
        Department department = departmentDao.getDepartment( "cs" );
        assert rubricDao.getDepartmentRubrics( department ).size() == 0;
    }

    @Test
    public void getPersonalRubrics()
    {
        User creator = userDao.getUserByUsername( "cysun" );
        assert rubricDao.getPersonalRubrics( creator ).size() == 1;
    }

}
