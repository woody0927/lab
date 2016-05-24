package idv.woody.infinispan.cache;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;

/**
 * Created by chun-chiao on 2016/4/30.
 */
@Listener
public class AddElementListener {
    @CacheEntryCreated
    public void print(CacheEntryCreatedEvent event) {
        if (event.isPre()) {
            return;
        }
        System.out.println(String.format("New entry %s with value %s created in the cache", event.getKey(), event.getValue()));
    }
    @CacheEntryRemoved
    public void observeRemove(CacheEntryRemovedEvent<String, String> event) {
        if (event.isPre())
            return;

        System.out.println(String.format("Cache entry %s removed in cache %s", event.getKey(), event.getCache()));
    }
}
