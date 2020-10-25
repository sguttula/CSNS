package csns.model.site.dao;

import csns.model.site.Announcement;

public interface AnnouncementDao {

    Announcement getAnnouncement( Long id );

    Announcement saveAnnouncement( Announcement announcement );

}
