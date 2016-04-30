package idv.woody.infinispan;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.remoting.transport.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LabCacheManager {
    @Autowired
    private DefaultCacheManager cacheManager;
    private boolean start = false;
    @Resource(name="cacheNames")
    private List<CacheName> cacheNames;
    private ConcurrentHashMap<CacheName, Cache> caches = new ConcurrentHashMap<CacheName, Cache>();

    @PostConstruct
    public void start() {
        if (start) {
            return;
        }
        for(CacheName cacheName : cacheNames) {
            // getCache(<cache_name>) starts the cache automatically, that's why explicit start is not needed
            caches.putIfAbsent(cacheName, cacheManager.getCache(cacheName.name()));
        }
        start = true;
    }

    public void stop() {
        for (Map.Entry<CacheName, Cache> entry : caches.entrySet()) {
            entry.getValue().stop();
        }
        cacheManager.stop();
        start = false;
    }

    public <K,V> Cache<K,V> getCache(CacheName cacheName) {
        return caches.get(cacheName);
    }

    public Address getAddress() {
        return cacheManager.getAddress();
    }

}
