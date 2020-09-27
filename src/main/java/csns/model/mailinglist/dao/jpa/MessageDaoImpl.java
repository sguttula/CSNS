package csns.model.mailinglist.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.mailinglist.Mailinglist;
import csns.model.mailinglist.Message;
import csns.model.mailinglist.dao.MessageDao;

@Repository
public class MessageDaoImpl implements MessageDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Message getMessage( Long id )
    {
        return entityManager.find( Message.class, id );
    }

    @Override
    public List<Message> getMessagess( Mailinglist mailinglist, int maxResults )
    {
        String query = "from Message where mailinglist = :mailinglist "
            + "order by date desc";

        return entityManager.createQuery( query, Message.class )
            .setParameter( "mailinglist", mailinglist )
            .setMaxResults( maxResults )
            .getResultList();
    }

    @Override
    public List<Message> searchMessages( Mailinglist mailinglist, String text,
        int maxResults )
    {
        return entityManager.createNamedQuery( "mailinglist.message.search",
            Message.class )
            .setParameter( "mailinglistId", mailinglist.getId() )
            .setParameter( "text", text )
            .setMaxResults( maxResults )
            .getResultList();
    }

    @Override
    @Transactional
    public Message saveMessage( Message message )
    {
        return entityManager.merge( message );
    }

}
