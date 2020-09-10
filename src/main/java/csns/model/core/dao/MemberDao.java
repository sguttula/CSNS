package csns.model.core.dao;

import csns.model.core.Group;
import csns.model.core.Member;
import csns.model.core.User;

public interface MemberDao {

    Member getMember( Long id );

    Member getMember( Group group, User user );

    Member saveMember( Member member );

    void deleteMember( Member member );

    void deleteMembers( Long ids[] );

}
