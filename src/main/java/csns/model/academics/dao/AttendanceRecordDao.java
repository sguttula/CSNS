package csns.model.academics.dao;

import csns.model.academics.AttendanceEvent;
import csns.model.academics.AttendanceRecord;
import csns.model.core.User;

public interface AttendanceRecordDao {

    AttendanceRecord getAttendanceRecord( Long id );

    AttendanceRecord getAttendanceRecord( AttendanceEvent event, User user );

    AttendanceRecord saveAttendanceRecord( AttendanceRecord record );

}
