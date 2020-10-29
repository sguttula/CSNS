package csns.model.survey.dao;

import csns.model.core.User;
import csns.model.survey.Survey;
import csns.model.survey.SurveyResponse;

public interface SurveyResponseDao {

    SurveyResponse getSurveyResponse( Long id );

    SurveyResponse getSurveyResponse( Survey survey, User user );

    SurveyResponse findSurveyResponse( Long answerSheetId );

    SurveyResponse saveSurveyResponse( SurveyResponse surveyResponse );

}
