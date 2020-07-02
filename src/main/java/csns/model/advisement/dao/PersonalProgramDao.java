package csns.model.advisement.dao;

import java.util.List;

import csns.model.academics.Program;
import csns.model.advisement.PersonalProgram;
import csns.model.advisement.PersonalProgramBlock;
import csns.model.advisement.PersonalProgramEntry;
import csns.model.core.User;

public interface PersonalProgramDao {

    PersonalProgram getPersonalProgram( Long id );

    PersonalProgram getPersonalProgram( User student, Program program );

    PersonalProgram getPersonalProgram( PersonalProgramBlock block );

    PersonalProgram getPersonalProgram( PersonalProgramEntry entry );

    List<PersonalProgram> getPersonalPrograms( User student );

    PersonalProgram savePersonalProgram( PersonalProgram program );

}
