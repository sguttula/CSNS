package csns.model.assessment.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.assessment.Program;

public interface ProgramDao {

    Program getProgram( Long id );

    List<Program> getPrograms( Department department );

    Program saveProgram( Program program );

}
