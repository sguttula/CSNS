package csns.model.site.dao;

import csns.model.site.Block;

public interface BlockDao {

    Block getBlock( Long id );

    Block saveBlock( Block block );

}
