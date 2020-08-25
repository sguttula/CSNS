package csns.model.assessment.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.assessment.Rubric;
import csns.model.core.User;

public interface RubricDao {

    Rubric getRubric( Long id );

    List<Rubric> getDepartmentRubrics( Department department );

    List<Rubric> getPublishedDepartmentRubrics( Department department );

    List<Rubric> getPersonalRubrics( User creator );

    List<Rubric> getPublishedPersonalRubrics( User creator );

    List<Rubric> searchRubrics( String text, int maxResults );

    Rubric saveRubric( Rubric rubric );

}
