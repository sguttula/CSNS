package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Course;

public interface CourseDao {

    Course getCourse( Long id );

    Course getCourse( String code );

    List<Course> searchCourses( String text, boolean includeObsolete,
        int maxResults );

    Course saveCourse( Course course );

}
