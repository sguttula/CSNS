package csns.model.prereg.dao;

import java.util.List;

import csns.model.core.User;
import csns.model.prereg.ScheduleRegistration;
import csns.model.prereg.Schedule;

public interface ScheduleRegistrationDao {

    ScheduleRegistration getScheduleRegistration( Long id );

    ScheduleRegistration getScheduleRegistration( User student,
        Schedule schedule );

    List<ScheduleRegistration> getScheduleRegistrations( Schedule schedule );

    ScheduleRegistration saveScheduleRegistration(
        ScheduleRegistration registration );

}
