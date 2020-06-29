package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.academics.Project;

public interface ProjectDao {

    Project getProject( Long id );

    List<Project> getProjects( Department department, int year );

    List<Integer> getProjectYears( Department department );

    List<Project> searchProjects( String text, int maxResults );

    Project saveProject( Project project );

}
