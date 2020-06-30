package csns.model.advisement.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.advisement.PersonalProgramEntry;
import csns.model.advisement.dao.PersonalProgramEntryDao;

@Repository
public class PersonalProgramEntryDaoImpl implements PersonalProgramEntryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersonalProgramEntry getPersonalProgramEntry( Long id )
    {
        return entityManager.find( PersonalProgramEntry.class, id );
    }

    @Override
    @Transactional
    public PersonalProgramEntry savePersonalProgramEntry(
        PersonalProgramEntry entry )
    {
        return entityManager.merge( entry );
    }

    @Override
    @Transactional
    public void deletePersonalProgramEntry( PersonalProgramEntry entry )
    {
        entityManager.remove( entry );
    }

}
