package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.AcademicStanding;
import csns.model.academics.Department;
import csns.model.academics.Standing;
import csns.model.core.User;

public interface AcademicStandingDao {

    AcademicStanding getAcademicStanding( Long id );

    AcademicStanding getAcademicStanding( User student, Department department,
        Standing standing );

    AcademicStanding getLatestAcademicStanding( User student,
        Department department );

    List<AcademicStanding> getAcademicStandings( User student );

    AcademicStanding saveAcademicStanding( AcademicStanding academicStanding );

    void deleteAcademicStanding( AcademicStanding academicStanding );

}
