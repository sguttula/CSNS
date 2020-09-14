package csns.model.core.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.core.File;
import csns.model.core.User;
import csns.model.core.dao.FileDao;

@Repository
public class FileDaoImpl implements FileDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PostAuthorize("returnObject.public or authenticated and (returnObject.owner.id == principal.id or principal.faculty or returnObject.submission != null and returnObject.submission.assignment.section.isInstructor(principal))")
    public File getFile( Long id )
    {
        return entityManager.find( File.class, id );
    }

    @Override
    @PreAuthorize("authenticated and (#parent == null or #owner.id == principal.id)")
    public List<File> getFiles( User owner, File parent, String name,
        boolean isFolder )
    {
        String query = "from File where owner = :owner and name = :name "
            + "and folder = :isFolder and deleted = false and ";
        query += parent != null ? "parent = :parent" : "parent is null";

        TypedQuery<File> typedQuery = entityManager.createQuery( query,
            File.class )
            .setParameter( "owner", owner )
            .setParameter( "name", name )
            .setParameter( "isFolder", isFolder );
        if( parent != null ) typedQuery.setParameter( "parent", parent );

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public File getCKEditorFolder( User owner )
    {
        String name = "ckeditor_files";
        String query = "from File where owner = :owner and name = :name "
            + "and folder = true and regular = false";

        List<File> results = entityManager.createQuery( query, File.class )
            .setParameter( "owner", owner )
            .setParameter( "name", name )
            .getResultList();

        if( results.size() > 0 ) return results.get( 0 );

        File file = new File();
        file.setName( name );
        file.setOwner( owner );
        file.setFolder( true );
        return saveFile( file );
    }

    @Override
    public List<File> listFiles( User owner )
    {
        String query = "from File where owner = :owner and parent is null "
            + "and regular = true and deleted = false "
            + "order by folder desc, name asc";

        return entityManager.createQuery( query, File.class )
            .setParameter( "owner", owner )
            .getResultList();
    }

    @Override
    public List<File> listFiles( File parent )
    {
        String query = "from File where parent = :parent and regular = true "
            + "and deleted = false order by folder desc, name asc";

        return entityManager.createQuery( query, File.class )
            .setParameter( "parent", parent )
            .getResultList();
    }

    @Override
    public List<File> listFolders( User owner )
    {
        String query = "from File where owner = :owner and parent is null "
            + "and folder = true and regular = true and deleted = false "
            + "order by name asc";

        return entityManager.createQuery( query, File.class )
            .setParameter( "owner", owner )
            .getResultList();
    }

    @Override
    public List<File> listFolders( File parent )
    {
        String query = "from File where parent = :parent and folder = true "
            + "and regular = true and deleted = false order by name asc";

        return entityManager.createQuery( query, File.class )
            .setParameter( "parent", parent )
            .getResultList();
    }

    @Override
    @Transactional
    @PreAuthorize("authenticated and (#file.parent == null and #file.owner.id == principal.id or principal.faculty or #file.submission != null and #file.submission.assignment.section.isInstructor(principal) or #file.parent != null and #file.parent.owner.id == principal.id)")
    public File saveFile( File file )
    {
        return entityManager.merge( file );
    }

    @Override
    public long getDiskUsage( User user )
    {
        String query = "select sum(size) from File where owner = :user and regular = true";

        Long result = entityManager.createQuery( query, Long.class )
            .setParameter( "user", user )
            .getSingleResult();
        return result != null ? result : 0;
    }

}
