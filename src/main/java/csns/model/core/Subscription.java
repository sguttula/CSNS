package csns.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import csns.model.academics.Term;
import csns.model.forum.Forum;
import csns.model.forum.Topic;
import csns.model.mailinglist.Mailinglist;
import csns.model.wiki.Page;

@Entity
@Table(name = "subscriptions")
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Any(metaColumn = @Column(name = "subscribable_type"),
        fetch = FetchType.EAGER)
    @AnyMetaDef(idType = "long", metaType = "string", metaValues = {
        @MetaValue(value = "FM", targetEntity = Forum.class),
        @MetaValue(value = "FT", targetEntity = Topic.class),
        @MetaValue(value = "WP", targetEntity = Page.class),
        @MetaValue(value = "ML", targetEntity = Mailinglist.class) })
    @JoinColumn(name = "subscribable_id")
    private Subscribable subscribable;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    private User subscriber;

    @Column(nullable = false)
    private Date date;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "code",
        column = @Column(name = "term")) })
    private Term term;

    /**
     * notificationSent is used as a flag to avoid repeated notifications. Some
     * subscribables like forum topics may use this flag to avoid sending
     * notifications to users repeatedly.
     */
    @Column(name = "notification_sent", nullable = false)
    private boolean notificationSent;

    /**
     * Some subscriptions are automatically created. For example, the students
     * who enrolled in a section are automatically subscribed to the class
     * forum. These subscriptions should be automatically terminated at some
     * point so the users don't need to manually unsubscribe themselves.
     */
    @Column(name = "auto_subscribed", nullable = false)
    private boolean autoSubscribed;

    public Subscription()
    {
        date = new Date();
        notificationSent = false;
        autoSubscribed = false;
    }

    public Subscription( Subscribable subscribable, User user )
    {
        this();
        this.subscribable = subscribable;
        this.subscriber = user;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Subscribable getSubscribable()
    {
        return subscribable;
    }

    public void setSubscribable( Subscribable subscribable )
    {
        this.subscribable = subscribable;
    }

    public User getSubscriber()
    {
        return subscriber;
    }

    public void setSubscriber( User subscriber )
    {
        this.subscriber = subscriber;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public Term getTerm()
    {
        return term;
    }

    public void setTerm( Term term )
    {
        this.term = term;
    }

    public boolean isNotificationSent()
    {
        return notificationSent;
    }

    public void setNotificationSent( boolean notificationSent )
    {
        this.notificationSent = notificationSent;
    }

    public boolean isAutoSubscribed()
    {
        return autoSubscribed;
    }

    public void setAutoSubscribed( boolean autoSubscribed )
    {
        this.autoSubscribed = autoSubscribed;
    }

}
