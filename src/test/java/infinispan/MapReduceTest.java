package infinispan;

import idv.woody.infinispan.MapReduceWordMapper;
import idv.woody.infinispan.MapReduceWordReducer;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.distexec.mapreduce.MapReduceTask;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.Test;

import java.util.Map;

/**
 * Created by chu-chiao on 2016/1/6.
 */
public class MapReduceTest {
    @Test
    public void wordCnt() {
        Configuration configuration = new ConfigurationBuilder().indexing().build();
        EmbeddedCacheManager cacheManager = new DefaultCacheManager();
        String cacheName = "WORD_LOCAL_CACHE";
        cacheManager.defineConfiguration(cacheName, configuration);
        Cache<String, String> cache = cacheManager.getCache(cacheName);
        cache.put("1", "Hello world here I am");
        cache.put("2", "Infinispan rules the world");
        cache.put("3", "JUDCon is in Boston");
        cache.put("4", "JBoss World is in Boston as well");
        cache.put("12","JBoss Application Server");
        cache.put("15", "Hello world");
        cache.put("14", "Infinispan community");
        cache.put("15", "Hello world");

        cache.put("111", "Infinispan open source");
        cache.put("112", "Boston is close to Toronto");
        cache.put("113", "Toronto is a capital of Ontario");
        cache.put("114", "JUDCon is cool");
        cache.put("211", "JBoss World is awesome");
        cache.put("212", "JBoss rules");
        cache.put("213", "JBoss division of RedHat ");
        cache.put("214", "RedHat community");

        MapReduceTask<String, String, String, Integer> task = new MapReduceTask<String, String, String, Integer>(cache);
        task.mappedWith(new MapReduceWordMapper()).reducedWith(new MapReduceWordReducer());
        Map<String, Integer> wordMap = task.execute();
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            System.out.println(String.format("key %s, value %d", entry.getKey(), entry.getValue()));
        }

        System.out.println(cacheManager.cacheExists(cacheName));
        cacheManager = new DefaultCacheManager();
        System.out.println(cacheManager.cacheExists(cacheName));
    }
}
