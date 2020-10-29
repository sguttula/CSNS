package csns.model.survey.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.core.User;
import csns.model.survey.Survey;
import csns.model.survey.SurveyResponse;
import csns.model.survey.dao.SurveyResponseDao;

@Repository
public class SurveyResponseDaoImpl implements SurveyResponseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SurveyResponse getSurveyResponse( Long id )
    {
        return entityManager.find( SurveyResponse.class, id );
    }

    @Override
    public SurveyResponse getSurveyResponse( Survey survey, User user )
    {
        String query = "from SurveyResponse where survey = :survey "
            + "and answerSheet.author = :user";

        List<SurveyResponse> results = entityManager.createQuery( query,
            SurveyResponse.class )
            .setParameter( "survey", survey )
            .setParameter( "user", user )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    public SurveyResponse findSurveyResponse( Long answerSheetId )
    {
        List<SurveyResponse> results = entityManager.createQuery(
            "from SurveyResponse where answerSheet.id = :answerSheetId",
            SurveyResponse.class )
            .setParameter( "answerSheetId", answerSheetId )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    @Transactional
    public SurveyResponse saveSurveyResponse( SurveyResponse surveyResponse )
    {
        return entityManager.merge( surveyResponse );
    }

}
