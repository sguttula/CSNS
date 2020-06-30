package csns.model.advisement.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.advisement.CourseWaiver;
import csns.model.advisement.dao.CourseWaiverDao;
import csns.model.core.User;

@Repository
public class CourseWaiverDaoImpl implements CourseWaiverDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CourseWaiver getCourseWaiver( Long id )
    {
        return entityManager.find( CourseWaiver.class, id );
    }

    @Override
    public List<CourseWaiver> getCourseWaivers( User student )
    {
        String query = "from CourseWaiver where advisementRecord.student = :student "
            + "order by course.code asc";

        return entityManager.createQuery( query, CourseWaiver.class )
            .setParameter( "student", student )
            .getResultList();
    }

    @Override
    @Transactional
    public CourseWaiver saveCourseWaiver( CourseWaiver courseWaiver )
    {
        return entityManager.merge( courseWaiver );
    }

    @Override
    @Transactional
    public void deleteCourseWaiver( CourseWaiver courseWaiver )
    {
        entityManager.remove( courseWaiver );
    }

}
