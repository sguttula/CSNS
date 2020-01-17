package csns.model.forum.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.forum.Forum;
import csns.model.forum.Post;

@Test(groups = "PostDaoTests", dependsOnGroups = "ForumDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class PostDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    ForumDao forumDao;

    @Autowired
    PostDao postDao;

    @Test
    public void searchPosts()
    {
        Forum forum = forumDao.getForum( 3000L );
        List<Post> posts = postDao.searchPosts( forum, "welcome", 10 );
        assert posts.size() == 1;
    }

}
