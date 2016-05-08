package infinispan;

import idv.woody.infinispan.DistributedCacheClient;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class DistributedCacheClientTest {
    @Autowired
    private DistributedCacheClient client;
    @Test
    public void testDistributedCache() throws IOException, InterruptedException {
        // you can see the values are shared between jgroup channel if two clients are runing at the same time
        client.run();
    }
}
