package csns.model.survey.dao.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.survey.Survey;
import csns.model.survey.dao.SurveyDao;

@Repository
public class SurveyDaoImpl implements SurveyDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Survey getSurvey( Long id )
    {
        return entityManager.find( Survey.class, id );
    }

    @Override
    public List<Survey> getOpenSurveys( Department department )
    {
        Calendar now = Calendar.getInstance();
        String query = "from Survey where publishDate < :now "
            + "and (closeDate is null or closeDate > :now) "
            + "and department = :department and deleted = false "
            + "order by name asc";

        return entityManager.createQuery( query, Survey.class )
            .setParameter( "now", now )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    @PreAuthorize("principal.isFaculty(#department.abbreviation)")
    public List<Survey> getClosedSurveys( Department department )
    {
        Calendar now = Calendar.getInstance();
        String query = "from Survey where publishDate is not null and "
            + "closeDate is not null and closeDate < :now "
            + "and department = :department and deleted = false "
            + "order by closeDate desc";

        return entityManager.createQuery( query, Survey.class )
            .setMaxResults( 25 )
            .setParameter( "now", now )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    @PreAuthorize("principal.isFaculty(#department.abbreviation)")
    public List<Survey> getUnpublishedSurveys( Department department )
    {
        Calendar now = Calendar.getInstance();
        String query = "from Survey where "
            + "(closeDate is null or closeDate > :now) "
            + "and (publishDate is null or publishDate > :now) "
            + "and department = :department and deleted = false "
            + "order by closeDate desc";

        return entityManager.createQuery( query, Survey.class )
            .setParameter( "now", now )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    @PreAuthorize("principal.isFaculty(#department.abbreviation)")
    public List<Survey> getSurveys( Department department )
    {
        String query = "from Survey where department = :department "
            + "and deleted = false order by date desc";

        return entityManager.createQuery( query, Survey.class )
            .setParameter( "department", department )
            .setMaxResults( 30 )
            .getResultList();
    }

    @Override
    @PreAuthorize("principal.isFaculty(#department.abbreviation)")
    public List<Survey> searchSurveys( Department department, String text,
        int maxResults )
    {
        TypedQuery<Survey> query = entityManager.createNamedQuery(
            "survey.search", Survey.class );
        if( maxResults > 0 ) query.setMaxResults( maxResults );
        return query.setParameter( "departmentId", department.getId() )
            .setParameter( "text", text )
            .getResultList();
    }

    @Override
    @PreAuthorize("principal.isFaculty(#department.abbreviation)")
    public List<Survey> searchSurveysByPrefix( Department department,
        String text, int maxResults )
    {
        String query = "from Survey where lower(name) like :text || '%' "
            + "and deleted = false order by name asc";

        return entityManager.createQuery( query, Survey.class )
            .setParameter( "text", text.toLowerCase() )
            .setMaxResults( maxResults )
            .getResultList();
    }

    @Override
    @Transactional
    @PreAuthorize("principal.isFaculty(#survey.department.abbreviation)")
    public Survey saveSurvey( Survey survey )
    {
        return entityManager.merge( survey );
    }

}
