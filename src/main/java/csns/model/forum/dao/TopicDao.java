package csns.model.forum.dao;

import csns.model.forum.Topic;

public interface TopicDao {

    Topic getTopic( Long id );

    Topic saveTopic( Topic topic );

}
