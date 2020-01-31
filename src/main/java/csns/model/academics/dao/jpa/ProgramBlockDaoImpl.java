package csns.model.academics.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.ProgramBlock;
import csns.model.academics.dao.ProgramBlockDao;

@Repository
public class ProgramBlockDaoImpl implements ProgramBlockDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProgramBlock getProgramBlock( Long id )
    {
        return entityManager.find( ProgramBlock.class, id );
    }

    @Override
    @Transactional
    public ProgramBlock saveProgramBlock( ProgramBlock block )
    {
        return entityManager.merge( block );
    }

}
