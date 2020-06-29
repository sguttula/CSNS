package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Grade;

public interface GradeDao {

    Grade getGrade( Long id );

    Grade getGrade( String symbol );

    List<Grade> getGrades();

}
