package csns.model.academics.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.AttendanceEvent;
import csns.model.academics.dao.AttendanceEventDao;

@Repository
public class AttendanceEventDaoImpl implements AttendanceEventDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AttendanceEvent getAttendanceEvent( Long id )
    {
        return entityManager.find( AttendanceEvent.class, id );
    }

    @Override
    @Transactional
    public AttendanceEvent saveAttendanceEvent( AttendanceEvent event )
    {
        return entityManager.merge( event );
    }

}
