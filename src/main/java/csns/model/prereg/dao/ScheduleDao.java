package csns.model.prereg.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.prereg.Schedule;

public interface ScheduleDao {

    Schedule getSchedule( Long id );

    List<Schedule> getSchedules( Department department );

    Schedule saveSchedule( Schedule schedule );

}
