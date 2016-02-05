package infinispan;

import idv.woody.infinispan.Cache;
import idv.woody.infinispan.WordCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.apache.commons.lang.RandomStringUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class CacheTest {

    @Autowired
    private WordCache wordCache;

    @Test
    public void testLocalCache() {
        wordCache.start();
        String key = randomAlphanumeric(8);
        String value = randomAlphanumeric(8);
        assertNull(wordCache.get(key));
        wordCache.put(key, value);
        assertEquals(value, wordCache.get(key));
        wordCache.stop();
    }
}
