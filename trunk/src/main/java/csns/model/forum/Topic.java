/*
 * This file is part of the CSNetwork Services (CSNS) project.
 * 
 * Copyright 2012, Chengyu Sun (csun@calstatela.edu).
 * 
 * CSNS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * CSNS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with CSNS. If not, see http://www.gnu.org/licenses/agpl.html.
 */
package csns.model.forum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import csns.model.core.Subscribable;
import csns.model.core.User;

@Entity
@Table(name = "forum_topics")
public class Topic implements Subscribable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private boolean pinned;

    @Column(name = "num_of_views", nullable = false)
    private int numOfViews;

    @OneToMany(mappedBy = "topic")
    @OrderBy("id asc")
    private List<Post> posts;

    @OneToOne
    @JoinColumn(name = "first_post_id")
    private Post firstPost;

    @OneToOne
    @JoinColumn(name = "last_post_id")
    private Post lastPost;

    @ManyToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @Column(nullable = false)
    private boolean deleted;

    public Topic()
    {
        pinned = false;
        numOfViews = 0;
        posts = new ArrayList<Post>();
        deleted = false;
    }

    @Override
    public String getName()
    {
        return firstPost.getSubject();
    }

    public Date getDate()
    {
        return firstPost.getDate();
    }

    public User getAuthor()
    {
        return firstPost.getAuthor();
    }

    public void addPost( Post post )
    {
        posts.add( post );
        lastPost = post;
        if( posts.size() == 1 ) firstPost = post;
    }

    public int getNumOfReplies()
    {
        return posts.size() - 1;
    }

    public boolean togglePinned()
    {
        return pinned = !pinned;
    }

    public void incrementNumOfViews()
    {
        ++numOfViews;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public boolean isPinned()
    {
        return pinned;
    }

    public void setPinned( boolean pinned )
    {
        this.pinned = pinned;
    }

    public int getNumOfViews()
    {
        return numOfViews;
    }

    public void setNumOfViews( int numOfViews )
    {
        this.numOfViews = numOfViews;
    }

    public List<Post> getPosts()
    {
        return posts;
    }

    public void setPosts( List<Post> posts )
    {
        this.posts = posts;
    }

    public Post getFirstPost()
    {
        return firstPost;
    }

    public void setFirstPost( Post firstPost )
    {
        this.firstPost = firstPost;
    }

    public Post getLastPost()
    {
        return lastPost;
    }

    public void setLastPost( Post lastPost )
    {
        this.lastPost = lastPost;
    }

    public Forum getForum()
    {
        return forum;
    }

    public void setForum( Forum forum )
    {
        this.forum = forum;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted( boolean deleted )
    {
        this.deleted = deleted;
    }

}
