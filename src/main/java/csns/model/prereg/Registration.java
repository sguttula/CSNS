package csns.model.prereg;

import java.util.Date;
import java.util.List;

import csns.model.core.User;

public interface Registration {

    Long getId();

    User getStudent();

    String getComments();

    Date getDate();

    List<SectionRegistration> getSectionRegistrations();

}
