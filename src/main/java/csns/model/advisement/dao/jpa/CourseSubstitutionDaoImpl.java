package csns.model.advisement.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.advisement.CourseSubstitution;
import csns.model.advisement.dao.CourseSubstitutionDao;
import csns.model.core.User;

@Repository
public class CourseSubstitutionDaoImpl implements CourseSubstitutionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CourseSubstitution getCourseSubstitution( Long id )
    {
        return entityManager.find( CourseSubstitution.class, id );
    }

    @Override
    public List<CourseSubstitution> getCourseSubstitutions( User student )
    {
        String query = "from CourseSubstitution where advisementRecord.student = :student "
            + "order by original.code asc";

        return entityManager.createQuery( query, CourseSubstitution.class )
            .setParameter( "student", student )
            .getResultList();
    }

    @Override
    @Transactional
    public CourseSubstitution saveCourseSubstitution(
        CourseSubstitution courseSubstitution )
    {
        return entityManager.merge( courseSubstitution );
    }

    @Override
    @Transactional
    public void deleteCourseSubstitution( CourseSubstitution courseSubstitution )
    {
        entityManager.remove( courseSubstitution );
    }

}
