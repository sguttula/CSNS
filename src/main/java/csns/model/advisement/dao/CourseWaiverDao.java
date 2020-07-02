package csns.model.advisement.dao;

import java.util.List;

import csns.model.advisement.CourseWaiver;
import csns.model.core.User;

public interface CourseWaiverDao {

    CourseWaiver getCourseWaiver( Long id );

    List<CourseWaiver> getCourseWaivers( User student );

    CourseWaiver saveCourseWaiver( CourseWaiver courseWaiver );

    void deleteCourseWaiver( CourseWaiver courseWaiver );

}
