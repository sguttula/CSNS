package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.CourseMapping;
import csns.model.academics.Department;

public interface CourseMappingDao {

    CourseMapping getCourseMapping( Long id );

    List<CourseMapping> getCourseMappings( Department department );

    CourseMapping saveCourseMapping( CourseMapping courseMapping );

}
