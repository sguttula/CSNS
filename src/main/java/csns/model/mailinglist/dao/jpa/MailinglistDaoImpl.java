package csns.model.mailinglist.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import csns.model.mailinglist.Mailinglist;
import csns.model.mailinglist.dao.MailinglistDao;

@Repository
public class MailinglistDaoImpl implements MailinglistDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mailinglist getMailinglist( Long id )
    {
        return entityManager.find( Mailinglist.class, id );
    }

}
