package idv.woody.infinispan;

import org.infinispan.Cache;
import org.infinispan.remoting.transport.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class DistributedCacheClient {
    @Autowired
    private LabCacheManager labCacheManager;

    public void run() throws IOException, InterruptedException {
        final Cache<String, String> cache = labCacheManager.getCache(CacheName.WORD_GLOBAL_CACHE);
        cache.addListener(new AddElementListener());
        final Address address = labCacheManager.getAddress();
        System.out.printf("Cache %s started on %s, cache members are now %s\n", CacheName.WORD_GLOBAL_CACHE, address,
                cache.getAdvancedCache().getRpcManager().getMembers());
        put(cache, address.toString(), "value");

        Thread putThread = new Thread() {
            @Override
            public void run() {
                int counter = 0;
                while (counter < 5) {
                    try {
                        cache.put(address.toString() + counter, address + "-" + counter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    counter++;

                    try {
                        printCache(cache);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };
        putThread.start();
        // wait for putThread to finish to continue
        putThread.join();
        labCacheManager.stop();
    }

    public void put(Cache<String, String> cache, String key, String value) {
        cache.putIfAbsent(key, value);
    }

    public void printCache(Cache<String, String> cache) {
        System.out.println("=========================");
        System.out.println(String.format("%d items in the cache", cache.size()));
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            System.out.println(String.format("key :%s, value: %s", entry.getKey(), entry.getValue()));
        }
    }
}
