package infinispan.cache;

import idv.woody.infinispan.cache.CacheName;
import idv.woody.infinispan.cache.LabCacheManager;
import org.infinispan.Cache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class LabCacheManagerTest {

    @Autowired
    private LabCacheManager labCacheManager;

    @Before
    public void init() {
        labCacheManager.start();
    }

    @After
    public void tearDown() {
        labCacheManager.stop();
    }

    @Test
    public void testLocalCache() {
        String key = randomAlphanumeric(8);
        String value = randomAlphanumeric(8);
        Cache<String, String> wordCache = labCacheManager.getCache(CacheName.WORD_LOCAL_CACHE);
        assertNull(wordCache.get(key));
        wordCache.put(key, value);
        assertEquals(value, wordCache.get(key));
    }
}
