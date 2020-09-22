package csns.model.forum.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.forum.Forum;
import csns.model.forum.Post;
import csns.model.forum.dao.PostDao;

@Repository
public class PostDaoImpl implements PostDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Post getPost( Long id )
    {
        return entityManager.find( Post.class, id );
    }

    @Override
    public List<Post> searchPosts( Forum forum, String text, int maxResults )
    {
        return entityManager.createNamedQuery( "forum.post.search", Post.class )
            .setParameter( "forumId", forum.getId() )
            .setParameter( "text", text )
            .setMaxResults( maxResults )
            .getResultList();
    }

    @Override
    @Transactional
    public Post savePost( Post post )
    {
        return entityManager.merge( post );
    }

}
