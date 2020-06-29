package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.academics.Program;

public interface ProgramDao {

    Program getProgram( Long id );

    List<Program> getPrograms( Department department );

    List<Program> getPublishedPrograms( Department department );

    List<Program> searchPrograms( String text, int maxResults );

    Program saveProgram( Program program );

}
