package csns.model.survey.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.survey.SurveyChart;
import csns.model.survey.dao.SurveyChartDao;

@Repository
public class SurveyChartDaoImpl implements SurveyChartDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SurveyChart getSurveyChart( Long id )
    {
        return entityManager.find( SurveyChart.class, id );
    }

    @Override
    public List<SurveyChart> getSurveyCharts( Department department )
    {
        String query = "from SurveyChart where department = :department "
            + "and deleted = false order by date desc";

        return entityManager.createQuery( query, SurveyChart.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    @Transactional
    @PreAuthorize("principal.isFaculty(#chart.department.abbreviation)")
    public SurveyChart saveSurveyChart( SurveyChart chart )
    {
        return entityManager.merge( chart );
    }

}
