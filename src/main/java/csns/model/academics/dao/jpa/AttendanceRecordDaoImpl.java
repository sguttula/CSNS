package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.AttendanceEvent;
import csns.model.academics.AttendanceRecord;
import csns.model.academics.dao.AttendanceRecordDao;
import csns.model.core.User;

@Repository
public class AttendanceRecordDaoImpl implements AttendanceRecordDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AttendanceRecord getAttendanceRecord( Long id )
    {
        return entityManager.find( AttendanceRecord.class, id );
    }

    @Override
    public AttendanceRecord getAttendanceRecord( AttendanceEvent event,
        User user )
    {
        String query = "from AttendanceRecord where event = :event and user = :user";

        List<AttendanceRecord> results = entityManager.createQuery( query,
            AttendanceRecord.class )
            .setParameter( "event", event )
            .setParameter( "user", user )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    @Transactional
    public AttendanceRecord saveAttendanceRecord( AttendanceRecord record )
    {
        return entityManager.merge( record );
    }

}
