package idv.woody.infinispan;

import com.google.common.collect.Lists;
import idv.woody.ConfigurationService;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
@Scope("singleton")
public class CacheManagerFactory {
    private DefaultCacheManager cacheManager;
    @Autowired
    private ConfigurationService configurationService;

    @Bean
    public DefaultCacheManager cacheManager() throws IOException {
        final InputStream configResource = Thread.currentThread().getContextClassLoader().getResourceAsStream(configurationService.getInfinispanConfigName());
        cacheManager = new DefaultCacheManager(configResource);
        return cacheManager;
    }

    @Bean (name = "cacheNames")
    public List<CacheName> cacheNames() {
        return Lists.newArrayList(CacheName.values());
    }
}
