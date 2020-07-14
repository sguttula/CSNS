package csns.model.assessment.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.assessment.CourseJournal;
import csns.model.assessment.dao.CourseJournalDao;

@Repository
public class CourseJournalDaoImpl implements CourseJournalDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CourseJournal getCourseJournal( Long id )
    {
        return entityManager.find( CourseJournal.class, id );
    }

    @Override
    public List<CourseJournal> getSubmittedCourseJournals( Department department )
    {
        String query = "from CourseJournal where approveDate is null and "
            + "submitDate is not null and section.course.department = :department";

        return entityManager.createQuery( query, CourseJournal.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    @Transactional
    public CourseJournal saveCourseJournal( CourseJournal courseJournal )
    {
        return entityManager.merge( courseJournal );
    }

}
