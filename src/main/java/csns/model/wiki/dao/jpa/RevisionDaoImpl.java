package csns.model.wiki.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.wiki.Page;
import csns.model.wiki.Revision;
import csns.model.wiki.dao.RevisionDao;

@Repository
public class RevisionDaoImpl implements RevisionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Revision getRevision( Long id )
    {
        return entityManager.find( Revision.class, id );
    }

    @Override
    public Revision getRevision( String path )
    {
        String query = "from Revision where page.path = :path order by date desc";

        List<Revision> results = entityManager.createQuery( query,
            Revision.class )
            .setParameter( "path", path )
            .setMaxResults( 1 )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    public List<Revision> getRevisions( Page page )
    {
        String query = "from Revision where page = :page order by date desc";

        return entityManager.createQuery( query, Revision.class )
            .setParameter( "page", page )
            .getResultList();
    }

    @Override
    @Transactional
    public Revision saveRevision( Revision revision )
    {
        return entityManager.merge( revision );
    }

}
