package csns.model.advisement.dao;

import csns.model.advisement.PersonalProgramBlock;

public interface PersonalProgramBlockDao {

    PersonalProgramBlock getPersonalProgramBlock( Long id );

    PersonalProgramBlock savePersonalProgramBlock( PersonalProgramBlock block );

}
