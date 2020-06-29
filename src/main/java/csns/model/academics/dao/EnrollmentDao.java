package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Course;
import csns.model.academics.Enrollment;
import csns.model.academics.Section;
import csns.model.academics.Term;
import csns.model.core.User;

public interface EnrollmentDao {

    Enrollment getEnrollment( Long id );

    Enrollment getEnrollment( Section section, User student );

    List<Enrollment> getEnrollments( User student );

    List<Enrollment> getEnrollments( Course course, Term term, User student );

    Enrollment saveEnrollment( Enrollment enrollment );

    void deleteEnrollment( Enrollment enrollment );

}
