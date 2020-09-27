package csns.model.prereg.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.core.User;
import csns.model.prereg.ScheduleRegistration;
import csns.model.prereg.Schedule;
import csns.model.prereg.dao.ScheduleRegistrationDao;

@Repository
public class ScheduleRegistrationDaoImpl implements ScheduleRegistrationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ScheduleRegistration getScheduleRegistration( Long id )
    {
        return entityManager.find( ScheduleRegistration.class, id );
    }

    @Override
    public ScheduleRegistration getScheduleRegistration( User student,
        Schedule schedule )
    {
        String query = "from ScheduleRegistration where student = :student "
            + "and schedule = :schedule";

        List<ScheduleRegistration> results = entityManager
            .createQuery( query, ScheduleRegistration.class )
            .setParameter( "student", student )
            .setParameter( "schedule", schedule )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    public List<ScheduleRegistration> getScheduleRegistrations(
        Schedule schedule )
    {
        String query = "from ScheduleRegistration r where r.schedule = :schedule "
            + "and r.sectionRegistrations is not empty order by r.date asc";

        return entityManager.createQuery( query, ScheduleRegistration.class )
            .setParameter( "schedule", schedule )
            .getResultList();
    }

    @Override
    @Transactional
    public ScheduleRegistration saveScheduleRegistration(
        ScheduleRegistration registration )
    {
        return entityManager.merge( registration );
    }

}
