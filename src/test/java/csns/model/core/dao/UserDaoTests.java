package csns.model.core.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.core.User;

@Test(groups = "UserDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class UserDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    UserDao userDao;

    @Test
    public void getUserById()
    {
        assert userDao.getUser( 1000L ) != null;
    }

    @Test
    public void getUserByCin()
    {
        assert userDao.getUserByCin( "1000" ) != null;
    }

    @Test
    public void getUserByUsername()
    {
        assert userDao.getUserByUsername( "cysun" ) != null;
        assert userDao.getUserByUsername( "jdoe1" ) != null;
        assert userDao.getUserByUsername( "jdoe2" ) != null;
    }

    @Test
    public void getUsers()
    {
        Long ids[] = { 1000002L, 1000003L };
        List<User> users = userDao.getUsers( ids );

        assert users.get( 0 ).getFirstName().equals( "Jane" );
        assert users.get( 1 ).getFirstName().equals( "John" );
    }

    @Test
    public void saveUser()
    {
        User user = new User();
        user.setCin( "123456789" );
        user.setUsername( "testuser1" );
        user.setPassword( "testuser1" );
        user.setLastName( "User" );
        user.setFirstName( "Test" );
        user.setPrimaryEmail( "testuser1@localhost.localdomain" );

        user = userDao.saveUser( user );
        assert user.getId() != null;
    }

}
