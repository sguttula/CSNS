package csns.model.forum.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.forum.Topic;
import csns.model.forum.dao.TopicDao;

@Repository
public class TopicDaoImpl implements TopicDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PostAuthorize("not returnObject.forum.membersOnly or authenticated and returnObject.forum.isMember(principal)")
    public Topic getTopic( Long id )
    {
        return entityManager.find( Topic.class, id );
    }

    @Override
    @Transactional
    public Topic saveTopic( Topic topic )
    {
        return entityManager.merge( topic );
    }

}
