package csns.model.core.dao;

import csns.model.core.Group;

public interface GroupDao {

    Group getGroup( Long id );

    Group saveGroup( Group group );

}
