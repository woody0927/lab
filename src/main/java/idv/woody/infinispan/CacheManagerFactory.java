package idv.woody.infinispan;

import org.infinispan.manager.DefaultCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@Scope("singleton")
public class CacheManagerFactory {
    private String configFileName = "local-cache.xml";
    private DefaultCacheManager cacheManager;

    @Bean
    public DefaultCacheManager cacheManager              () throws IOException {
        final InputStream configResource = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName);
        cacheManager = new DefaultCacheManager(configResource);
        return cacheManager;
    }
}
