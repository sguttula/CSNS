package csns.model.core.dao.jpa;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.core.Group;
import csns.model.core.Member;
import csns.model.core.User;
import csns.model.core.dao.MemberDao;

@Repository
public class MemberDaoImpl implements MemberDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Member getMember( Long id )
    {
        return entityManager.find( Member.class, id );
    }

    @Override
    public Member getMember( Group group, User user )
    {
        String query = "from Member where group = :group and user = :user";

        List<Member> members = entityManager.createQuery( query, Member.class )
            .setParameter( "group", group )
            .setParameter( "user", user )
            .getResultList();
        return members.size() == 0 ? null : members.get( 0 );
    }

    @Override
    @Transactional
    public Member saveMember( Member member )
    {
        return entityManager.merge( member );
    }

    @Override
    @Transactional
    public void deleteMember( Member member )
    {
        entityManager.remove( member );
    }

    @Override
    @Transactional
    public void deleteMembers( Long[] ids )
    {
        String query = "delete from Member where id in (:ids)";

        entityManager.createQuery( query )
            .setParameter( "ids", Arrays.asList( ids ) )
            .executeUpdate();
    }

}
