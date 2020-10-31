package csns.model.survey.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.survey.SurveyChart;

public interface SurveyChartDao {

    SurveyChart getSurveyChart( Long id );

    List<SurveyChart> getSurveyCharts( Department department );

    SurveyChart saveSurveyChart( SurveyChart chart );

}
