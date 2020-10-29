package csns.model.survey.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.survey.SurveyChartSeries;
import csns.model.survey.dao.SurveyChartSeriesDao;

@Repository
public class SurveyChartSeriesDaoImpl implements SurveyChartSeriesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SurveyChartSeries getSurveyChartSeries( Long id )
    {
        return entityManager.find( SurveyChartSeries.class, id );
    }

    @Override
    @Transactional
    public SurveyChartSeries saveSurveyChartSeries( SurveyChartSeries series )
    {
        return entityManager.merge( series );
    }

}
