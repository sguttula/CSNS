package csns.model.advisement.dao;

import java.util.List;

import csns.model.advisement.AdvisementRecord;
import csns.model.core.User;

public interface AdvisementRecordDao {

    AdvisementRecord getAdvisementRecord( Long id );

    List<AdvisementRecord> getAdvisementRecords( User student );

    AdvisementRecord saveAdvisementRecord( AdvisementRecord advisementRecord );

}
