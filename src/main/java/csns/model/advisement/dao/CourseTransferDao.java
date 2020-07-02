package csns.model.advisement.dao;

import java.util.List;

import csns.model.advisement.CourseTransfer;
import csns.model.core.User;

public interface CourseTransferDao {

    CourseTransfer getCourseTransfer( Long id );

    List<CourseTransfer> getCourseTransfers( User student );

    CourseTransfer saveCourseTransfer( CourseTransfer courseTransfer );

    void deleteCourseTransfer( CourseTransfer courseTransfer );

}
