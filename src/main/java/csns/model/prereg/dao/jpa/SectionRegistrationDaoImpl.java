package csns.model.prereg.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.core.User;
import csns.model.prereg.Section;
import csns.model.prereg.SectionRegistration;
import csns.model.prereg.dao.SectionRegistrationDao;

@Repository
public class SectionRegistrationDaoImpl implements SectionRegistrationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SectionRegistration getSectionRegistration( Long id )
    {
        return entityManager.find( SectionRegistration.class, id );
    }

    @Override
    public SectionRegistration getSectionRegistration( User student,
        Section section )
    {
        String query = "from SectionRegistration where student = :student "
            + "and section = :section";

        List<SectionRegistration> results = entityManager
            .createQuery( query, SectionRegistration.class )
            .setParameter( "student", student )
            .setParameter( "section", section )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    @Transactional
    public SectionRegistration saveSectionRegistration(
        SectionRegistration registration )
    {
        return entityManager.merge( registration );
    }

    @Override
    @Transactional
    public void deleteSectionRegistration( SectionRegistration registration )
    {
        entityManager.remove( registration );
    }

}
