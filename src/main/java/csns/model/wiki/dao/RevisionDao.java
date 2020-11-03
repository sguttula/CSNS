package csns.model.wiki.dao;

import java.util.List;

import csns.model.wiki.Page;
import csns.model.wiki.Revision;

public interface RevisionDao {

    Revision getRevision( Long id );

    /** Returns the latest revision at the path. */
    Revision getRevision( String path );

    List<Revision> getRevisions( Page page );

    Revision saveRevision( Revision revision );

}
