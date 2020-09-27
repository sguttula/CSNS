package csns.model.prereg.dao;

import csns.model.core.User;
import csns.model.prereg.Section;
import csns.model.prereg.SectionRegistration;

public interface SectionRegistrationDao {

    SectionRegistration getSectionRegistration( Long id );

    SectionRegistration getSectionRegistration( User student, Section section );

    SectionRegistration saveSectionRegistration(
        SectionRegistration registration );

    void deleteSectionRegistration( SectionRegistration registration );

}
