package csns.model.assessment.dao;

import csns.model.assessment.ProgramObjective;

public interface ProgramObjectiveDao {

    ProgramObjective getProgramObjective( Long id );

    ProgramObjective saveProgramObjective( ProgramObjective objective );

}
