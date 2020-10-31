package csns.model.survey.dao;

import csns.model.survey.SurveyChartSeries;

public interface SurveyChartSeriesDao {

    SurveyChartSeries getSurveyChartSeries( Long id );

    SurveyChartSeries saveSurveyChartSeries( SurveyChartSeries series );

}
