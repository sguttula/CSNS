package csns.web.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import csns.model.core.User;
import csns.model.core.dao.UserDao;

@RestController
@SuppressWarnings("deprecation")
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory
        .getLogger( UserService.class );

    @RequestMapping("/service/user/login")
    public User login( @RequestParam String username,
        @RequestParam String password, ModelMap models )
    {
        User user = userDao.getUserByUsername( username );
        if( user == null || !passwordEncoder.encodePassword( password, null )
            .equals( user.getPassword() ) )
        {
            logger
                .info( "Username or password does not match for " + username );
            user = null;
        }
        else
        {
            logger.info( "Credentials verified for " + username );
            if( user.getAccessKey() == null )
            {
                user.setAccessKey( UUID.randomUUID().toString() );
                user = userDao.saveUser( user );
                logger.info( "Access key generated for " + username );
            }
        }
        return user;
    }

}
