package csns.model.wiki.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.helper.WikiSearchResult;
import csns.model.wiki.Page;
import csns.model.wiki.dao.PageDao;

@Repository
public class PageDaoImpl implements PageDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page getPage( Long id )
    {
        return entityManager.find( Page.class, id );
    }

    @Override
    public Page getPage( String path )
    {
        List<Page> results = entityManager.createQuery(
            "from Page where path = :path", Page.class )
            .setParameter( "path", path )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    public List<WikiSearchResult> searchPages( String text, int maxResults )
    {
        return entityManager.createNamedQuery( "wiki.page.search",
            WikiSearchResult.class )
            .setParameter( "text", text )
            .setMaxResults( maxResults )
            .getResultList();
    }

    @Override
    @Transactional
    public Page savePage( Page page )
    {
        return entityManager.merge( page );
    }

}
