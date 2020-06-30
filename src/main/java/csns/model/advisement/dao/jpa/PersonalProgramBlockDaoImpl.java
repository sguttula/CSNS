package csns.model.advisement.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.advisement.PersonalProgramBlock;
import csns.model.advisement.dao.PersonalProgramBlockDao;

@Repository
public class PersonalProgramBlockDaoImpl implements PersonalProgramBlockDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersonalProgramBlock getPersonalProgramBlock( Long id )
    {
        return entityManager.find( PersonalProgramBlock.class, id );
    }

    @Override
    @Transactional
    public PersonalProgramBlock savePersonalProgramBlock(
        PersonalProgramBlock block )
    {
        return entityManager.merge( block );
    }

}
