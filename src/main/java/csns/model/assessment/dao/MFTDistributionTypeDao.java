package csns.model.assessment.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.assessment.MFTDistributionType;

public interface MFTDistributionTypeDao {

    MFTDistributionType getDistributionType( Long id );

    MFTDistributionType getDistributionType( Department department, String alias );

    List<MFTDistributionType> getDistributionTypes( Department department );

}
