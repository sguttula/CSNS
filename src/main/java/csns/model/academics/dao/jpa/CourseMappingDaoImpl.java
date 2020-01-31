package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.CourseMapping;
import csns.model.academics.Department;
import csns.model.academics.dao.CourseMappingDao;

@Repository
public class CourseMappingDaoImpl implements CourseMappingDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CourseMapping getCourseMapping( Long id )
    {
        return entityManager.find( CourseMapping.class, id );
    }

    @Override
    public List<CourseMapping> getCourseMappings( Department department )
    {
        String query = "from CourseMapping where department = :department "
            + "and deleted = false order by id asc";

        return entityManager.createQuery( query, CourseMapping.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    @Transactional
    public CourseMapping saveCourseMapping( CourseMapping courseMapping )
    {
        return entityManager.merge( courseMapping );
    }

}
