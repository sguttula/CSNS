package csns.model.core.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.core.Resource;
import csns.model.core.dao.ResourceDao;

@Repository
public class ResourceDaoImpl implements ResourceDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Resource getResource( Long id )
    {
        return entityManager.find( Resource.class, id );
    }

    @Override
    @Transactional
    public Resource saveResource( Resource resource )
    {
        return entityManager.merge( resource );
    }

}
