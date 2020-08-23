package csns.model.assessment.dao;

import java.util.Date;
import java.util.List;

import csns.model.academics.Department;
import csns.model.assessment.MFTScore;
import csns.model.core.User;

public interface MFTScoreDao {

    List<Date> getDates( Department department );

    List<Integer> getYears( Department department );

    MFTScore getScore( Department department, Date date, User user );

    List<MFTScore> getScores( Department department, Date date );

    List<MFTScore> getScores( Department department, Integer year );

    List<MFTScore> getMedianScores( Department department, Integer beginYear,
        Integer endYear );

    MFTScore saveScore( MFTScore score );

}
