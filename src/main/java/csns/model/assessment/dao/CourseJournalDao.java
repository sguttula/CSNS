package csns.model.assessment.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.assessment.CourseJournal;

public interface CourseJournalDao {

    CourseJournal getCourseJournal( Long id );

    List<CourseJournal> getSubmittedCourseJournals( Department department );

    CourseJournal saveCourseJournal( CourseJournal courseJournal );

}
