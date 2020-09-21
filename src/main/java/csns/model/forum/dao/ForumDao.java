package csns.model.forum.dao;

import java.util.List;

import csns.model.academics.Course;
import csns.model.academics.Department;
import csns.model.forum.Forum;

public interface ForumDao {

    Forum getForum( Long id );

    /**
     * Get the forum by the given name. It is the caller's responsibility to
     * make sure that the forum exists and the name is unique; otherwise an
     * exception will be raised.
     */
    Forum getForum( String name );

    Forum getForum( Course course );

    List<Forum> getSystemForums();

    List<Forum> getCourseForums( Department department );

    List<Forum> searchForums( String text, int maxResults );

    Forum saveForum( Forum forum );

}
