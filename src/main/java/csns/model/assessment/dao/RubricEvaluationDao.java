package csns.model.assessment.dao;

import java.util.List;

import csns.helper.RubricEvaluationStats;
import csns.model.academics.Course;
import csns.model.academics.Section;
import csns.model.assessment.Rubric;
import csns.model.assessment.RubricEvaluation;

public interface RubricEvaluationDao {
 
    RubricEvaluation getRubricEvaluation( Long id );

    List<RubricEvaluationStats> getRubricEvaluationStats( Rubric rubric,
        Section section, RubricEvaluation.Type type );

    List<RubricEvaluationStats> getRubricEvaluationStats( Rubric rubric,
        Course course, RubricEvaluation.Type type, int beginYear, int endYear );

    List<Integer> getRubricEvaluationYears( Rubric rubric, Course course );

    List<RubricEvaluationStats> getRubricEvaluationCounts( Rubric rubric,
        Course course, int beginYear, int endYear );

    RubricEvaluation saveRubricEvaluation( RubricEvaluation evaluation );

}
