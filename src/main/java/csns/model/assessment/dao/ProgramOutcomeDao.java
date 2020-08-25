package csns.model.assessment.dao;

import csns.model.assessment.ProgramOutcome;

public interface ProgramOutcomeDao {

    ProgramOutcome getProgramOutcome( Long id );

    ProgramOutcome saveProgramOutcome( ProgramOutcome outcome );

}
