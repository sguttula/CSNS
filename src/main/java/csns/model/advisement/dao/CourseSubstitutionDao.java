package csns.model.advisement.dao;

import java.util.List;

import csns.model.advisement.CourseSubstitution;
import csns.model.core.User;

public interface CourseSubstitutionDao {

    CourseSubstitution getCourseSubstitution( Long id );

    List<CourseSubstitution> getCourseSubstitutions( User student );

    CourseSubstitution saveCourseSubstitution(
        CourseSubstitution courseSubstitution );

    void deleteCourseSubstitution( CourseSubstitution courseSubstitution );

}
