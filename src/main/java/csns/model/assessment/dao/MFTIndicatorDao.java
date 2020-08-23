package csns.model.assessment.dao;

import java.util.Date;
import java.util.List;

import csns.model.academics.Department;
import csns.model.assessment.MFTIndicator;

public interface MFTIndicatorDao {

    List<Integer> getYears( Department department );

    MFTIndicator getIndicator( Long id );

    MFTIndicator getIndicator( Department department, Date date );

    List<MFTIndicator> getIndicators( Department department );

    List<MFTIndicator> getIndicators( Department department, Integer beginYear,
        Integer endYear );

    MFTIndicator saveIndicator( MFTIndicator indicator );

}
