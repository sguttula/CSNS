package csns.model.prereg.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.prereg.Schedule;
import csns.model.prereg.dao.ScheduleDao;

@Repository
public class ScheduleDaoImpl implements ScheduleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Schedule getSchedule( Long id )
    {
        return entityManager.find( Schedule.class, id );
    }

    @Override
    public List<Schedule> getSchedules( Department department )
    {
        String query = "from Schedule where department = :department "
            + "and deleted = false order by term desc";

        return entityManager.createQuery( query, Schedule.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    @Transactional
    public Schedule saveSchedule( Schedule schedule )
    {
        return entityManager.merge( schedule );
    }

}
