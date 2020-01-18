package csns.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import csns.model.core.dao.SubscriptionDao;

@Component
public class SimpleTasks {

    private static final Logger logger = LoggerFactory.getLogger( SimpleTasks.class );

    @Autowired
    SubscriptionDao subscriptionDao;

    // Run this task every Monday at 1:01:01AM.
    @Scheduled(cron = "1 1 1 ? * 1")
    public void autoUnsubscribe()
    {
        int n = subscriptionDao.autoUnsubscribe();
        logger.info( "Removed " + n + " subscriptions." );
    }

}
