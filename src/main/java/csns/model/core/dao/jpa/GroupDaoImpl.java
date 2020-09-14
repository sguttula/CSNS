package csns.model.core.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.core.Group;
import csns.model.core.dao.GroupDao;

@Repository
public class GroupDaoImpl implements GroupDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Group getGroup( Long id )
    {
        return entityManager.find( Group.class, id );
    }

    @Override
    @Transactional
    public Group saveGroup( Group group )
    {
        return entityManager.merge( group );
    }

}
