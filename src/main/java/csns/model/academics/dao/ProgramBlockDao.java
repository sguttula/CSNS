package csns.model.academics.dao;

import csns.model.academics.ProgramBlock;

public interface ProgramBlockDao {

    ProgramBlock getProgramBlock( Long id );

    ProgramBlock saveProgramBlock( ProgramBlock block );

}
