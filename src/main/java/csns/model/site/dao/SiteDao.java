package csns.model.site.dao;

import java.util.List;

import csns.model.academics.Course;
import csns.model.core.User;
import csns.model.site.Site;

public interface SiteDao {

    Site getSite( Long id );

    List<Site> getSites( Course course, User instructor, int maxResults );

    Site saveSite( Site site );

}
