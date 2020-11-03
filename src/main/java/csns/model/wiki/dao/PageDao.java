package csns.model.wiki.dao;

import java.util.List;

import csns.helper.WikiSearchResult;
import csns.model.wiki.Page;

public interface PageDao {

    Page getPage( Long id );

    Page getPage( String path );

    List<WikiSearchResult> searchPages( String text, int maxResults );

    Page savePage( Page page );

}
