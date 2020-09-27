package csns.model.prereg.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.prereg.Section;
import csns.model.prereg.dao.SectionDao;

@Repository("preregSectionDaoImpl")
public class SectionDaoImpl implements SectionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Section getSection( Long id )
    {
        return entityManager.find( Section.class, id );
    }

    @Override
    @Transactional
    public Section saveSection( Section section )
    {
        return entityManager.merge( section );
    }

}
