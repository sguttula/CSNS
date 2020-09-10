package csns.model.core.dao;

import java.util.List;

import csns.model.core.Subscribable;
import csns.model.core.Subscription;
import csns.model.core.User;

public interface SubscriptionDao {

    Subscription getSubscription( Long id );

    Subscription getSubscription( Subscribable subscribable, User subscriber );

    List<Subscription> getSubscriptions( Subscribable subscribable );

    List<Subscription> getSubscriptions( User subscriber, Class<?> clazz );

    long getSubscriptionCount( Subscribable subscribable );

    Subscription subscribe( Subscribable subscribable, User subscriber );

    void unsubscribe( Subscribable subscribable, User subscriber );

    Subscription saveSubscription( Subscription subscription );

    int autoUnsubscribe();

}
