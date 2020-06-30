package csns.model.advisement.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.advisement.CourseTransfer;
import csns.model.advisement.dao.CourseTransferDao;
import csns.model.core.User;

@Repository
public class CourseTransferDaoImpl implements CourseTransferDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CourseTransfer getCourseTransfer( Long id )
    {
        return entityManager.find( CourseTransfer.class, id );
    }

    @Override
    public List<CourseTransfer> getCourseTransfers( User student )
    {
        String query = "from CourseTransfer where advisementRecord.student = :student "
            + "order by course.code asc";

        return entityManager.createQuery( query, CourseTransfer.class )
            .setParameter( "student", student )
            .getResultList();
    }

    @Override
    @Transactional
    public CourseTransfer saveCourseTransfer( CourseTransfer courseTransfer )
    {
        return entityManager.merge( courseTransfer );
    }

    @Override
    @Transactional
    public void deleteCourseTransfer( CourseTransfer courseTransfer )
    {
        entityManager.remove( courseTransfer );
    }

}
