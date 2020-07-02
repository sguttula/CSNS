package csns.model.advisement.dao;

import csns.model.advisement.PersonalProgramEntry;

public interface PersonalProgramEntryDao {

    PersonalProgramEntry getPersonalProgramEntry( Long id );

    PersonalProgramEntry savePersonalProgramEntry( PersonalProgramEntry entry );

    void deletePersonalProgramEntry( PersonalProgramEntry entry );

}
