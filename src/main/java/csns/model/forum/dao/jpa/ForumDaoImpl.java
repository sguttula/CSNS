package csns.model.forum.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Course;
import csns.model.academics.Department;
import csns.model.forum.Forum;
import csns.model.forum.dao.ForumDao;

@Repository
public class ForumDaoImpl implements ForumDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PostAuthorize("not returnObject.membersOnly or authenticated and returnObject.isMember(principal)")
    public Forum getForum( Long id )
    {
        return entityManager.find( Forum.class, id );
    }

    @Override
    public Forum getForum( String name )
    {
        List<Forum> results = entityManager
            .createQuery( "from Forum where name = :name", Forum.class )
            .setParameter( "name", name )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    public Forum getForum( Course course )
    {
        List<Forum> results = entityManager
            .createQuery( "from Forum where course = :course", Forum.class )
            .setParameter( "course", course )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    public List<Forum> getSystemForums()
    {
        String query = "from Forum where department is null and course is null "
            + "and hidden = false";

        return entityManager.createQuery( query, Forum.class ).getResultList();
    }

    @Override
    public List<Forum> getCourseForums( Department department )
    {
        String query = "from Forum where course.department = :department "
            + "and course.obsolete = false order by course.code asc";

        return entityManager.createQuery( query, Forum.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    public List<Forum> searchForums( String text, int maxResults )
    {
        TypedQuery<Forum> query = entityManager
            .createNamedQuery( "forum.search", Forum.class );
        if( maxResults > 0 ) query.setMaxResults( maxResults );
        return query.setParameter( "text", text ).getResultList();
    }

    @Override
    @Transactional
    public Forum saveForum( Forum forum )
    {
        return entityManager.merge( forum );
    }

}
