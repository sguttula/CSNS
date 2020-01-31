package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Course;
import csns.model.academics.dao.CourseDao;

@Repository
public class CourseDaoImpl implements CourseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Course getCourse( Long id )
    {
        return entityManager.find( Course.class, id );
    }

    @Override
    public Course getCourse( String code )
    {
        List<Course> courses = entityManager
            .createQuery( "from Course where code = :code", Course.class )
            .setParameter( "code", code.toUpperCase() )
            .getResultList();
        return courses.size() == 0 ? null : courses.get( 0 );
    }

    @Override
    public List<Course> searchCourses( String text, boolean includeObsolete,
        int maxResults )
    {
        TypedQuery<Course> query = entityManager
            .createNamedQuery( "course.search", Course.class )
            .setParameter( "text", text )
            .setParameter( "includeObsolete", includeObsolete );
        if( maxResults > 0 ) query.setMaxResults( maxResults );
        return query.getResultList();
    }

    @Override
    @Transactional
    @PreAuthorize("principal.faculty")
    public Course saveCourse( Course course )
    {
        return entityManager.merge( course );
    }

}
