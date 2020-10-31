package csns.model.survey.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.survey.Survey;

public interface SurveyDao {

    Survey getSurvey( Long id );

    List<Survey> getOpenSurveys( Department department );

    List<Survey> getClosedSurveys( Department department );

    List<Survey> getUnpublishedSurveys( Department department );

    List<Survey> getSurveys( Department department );

    List<Survey> searchSurveys( Department department, String text,
        int maxResults );

    List<Survey> searchSurveysByPrefix( Department department, String text,
        int maxResults );

    Survey saveSurvey( Survey survey );

}
