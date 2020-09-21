package csns.model.forum.dao;

import java.util.List;

import csns.model.forum.Forum;
import csns.model.forum.Post;

public interface PostDao {

    Post getPost( Long id );

    List<Post> searchPosts( Forum forum, String text, int maxResults );

    Post savePost( Post post );

}
