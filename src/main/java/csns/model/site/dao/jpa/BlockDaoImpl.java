package csns.model.site.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.site.Block;
import csns.model.site.dao.BlockDao;

@Repository
public class BlockDaoImpl implements BlockDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Block getBlock( Long id )
    {
        return entityManager.find( Block.class, id );
    }

    @Override
    @Transactional
    public Block saveBlock( Block block )
    {
        return entityManager.merge( block );
    }

}
