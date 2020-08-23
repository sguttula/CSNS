package csns.model.assessment.dao;

import java.util.Date;
import java.util.List;

import csns.model.academics.Department;
import csns.model.assessment.MFTDistribution;
import csns.model.assessment.MFTDistributionType;

public interface MFTDistributionDao {

    List<Integer> getYears( Department department );

    MFTDistribution getDistribution( Long id );

    MFTDistribution getDistribution( Integer year, MFTDistributionType type );

    MFTDistribution getDistribution( Date date, MFTDistributionType type );

    List<MFTDistribution> getDistributions( Integer year,
        Department department );

    List<MFTDistribution> getDistributions( MFTDistributionType type );

    MFTDistribution saveDistribution( MFTDistribution distribution );

}
