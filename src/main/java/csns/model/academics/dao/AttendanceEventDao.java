package csns.model.academics.dao;

import csns.model.academics.AttendanceEvent;

public interface AttendanceEventDao {

    AttendanceEvent getAttendanceEvent( Long id );

    AttendanceEvent saveAttendanceEvent( AttendanceEvent event );

}
