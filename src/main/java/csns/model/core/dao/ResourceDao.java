package csns.model.core.dao;

import csns.model.core.Resource;

public interface ResourceDao {

    Resource getResource( Long id );

    Resource saveResource( Resource resource );

}
