package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.AcademicStanding;
import csns.model.academics.Department;
import csns.model.academics.Standing;
import csns.model.academics.dao.AcademicStandingDao;
import csns.model.core.User;

@Repository
public class AcademicStandingDaoImpl implements AcademicStandingDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AcademicStanding getAcademicStanding( Long id )
    {
        return entityManager.find( AcademicStanding.class, id );
    }

    @Override
    public AcademicStanding getAcademicStanding( User student,
        Department department, Standing standing )
    {
        String query = "from AcademicStanding where student = :student "
            + "and department = :department and standing = :standing";

        List<AcademicStanding> results = entityManager.createQuery( query,
            AcademicStanding.class )
            .setParameter( "student", student )
            .setParameter( "department", department )
            .setParameter( "standing", standing )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    public AcademicStanding getLatestAcademicStanding( User student,
        Department department )
    {
        String query = "from AcademicStanding where student = :student "
            + "and department = :department "
            + "order by term.code desc, standing.id desc";

        List<AcademicStanding> results = entityManager.createQuery( query,
            AcademicStanding.class )
            .setParameter( "student", student )
            .setParameter( "department", department )
            .setMaxResults( 1 )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    public List<AcademicStanding> getAcademicStandings( User student )
    {
        String query = "from AcademicStanding where student = :student "
            + "order by department.name asc, standing.id desc";

        return entityManager.createQuery( query, AcademicStanding.class )
            .setParameter( "student", student )
            .getResultList();
    }

    @Override
    @Transactional
    public AcademicStanding saveAcademicStanding(
        AcademicStanding academicStanding )
    {
        return entityManager.merge( academicStanding );
    }

    @Override
    @Transactional
    public void deleteAcademicStanding( AcademicStanding academicStanding )
    {
        entityManager.remove( academicStanding );
    }

}
