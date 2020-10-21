package csns.model.site.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.site.Announcement;
import csns.model.site.dao.AnnouncementDao;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Announcement getAnnouncement( Long id )
    {
        return entityManager.find( Announcement.class, id );
    }

    @Override
    @Transactional
    public Announcement saveAnnouncement( Announcement announcement )
    {
        return entityManager.merge( announcement );
    }

}
