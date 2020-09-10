package csns.model.core.dao;

import java.util.List;

import csns.model.core.User;

public interface UserDao {

    User getUser( Long id );

    User getUserByCin( String cin );

    User getUserByUsername( String username );

    User getUserByEmail( String email );

    List<User> getUsers( Long ids[] );

    List<User> getUsers( String lastName, String firstName );

    List<User> searchUsers( String text );

    List<User> searchUsersByPrefix( String text, int maxResults );

    List<User> searchUsersByStanding( String dept, String sybmol );

    User saveUser( User user );

}
