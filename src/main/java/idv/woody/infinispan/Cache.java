package idv.woody.infinispan;

import org.apache.commons.io.IOUtils;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

public class Cache<T> {
    @Autowired
    private DefaultCacheManager cacheManager;
    private String cacheName;
    private org.infinispan.Cache<String, T> cache;
    private boolean start = false;

    public Cache(String cacheName) {
        this.cacheName = cacheName;
    }

    public void start() {
        if (start) {
            return;
        }
        // getCache(<cache_name>) starts the cache automatically, that's why explicit start is not needed                                                                                                                   
        cache = cacheManager.getCache(cacheName);
        if (cache.getStatus().allowInvocations()) {
            start = true;
        }
    }

    public void stop() {
        if(start && cache != null) {
            cache.stop();
        }
    }

    public void put(String key, T value) {
        cache.put(key, value);
    }

    public T get(String key) {
        return cache.get(key);
    }
}
