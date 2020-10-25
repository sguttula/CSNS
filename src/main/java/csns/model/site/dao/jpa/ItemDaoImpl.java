package csns.model.site.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.site.Item;
import csns.model.site.dao.ItemDao;

@Repository
public class ItemDaoImpl implements ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Item getItem( Long id )
    {
        return entityManager.find( Item.class, id );
    }

    @Override
    @Transactional
    public Item saveItem( Item item )
    {
        return entityManager.merge( item );
    }

}
