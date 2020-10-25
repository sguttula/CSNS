package csns.model.site.dao;

import csns.model.site.Item;

public interface ItemDao {

    Item getItem( Long id );

    Item saveItem( Item item );

}
