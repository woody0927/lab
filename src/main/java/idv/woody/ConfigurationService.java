package idv.woody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by chun-chiao on 2016/5/8.
 */
@Service
@Scope("singleton")
public class ConfigurationService {

    @Value("#{config['lab.infinispan.configFileName']}")
    private String infinispanConfigName;

    public String getInfinispanConfigName() {
        return infinispanConfigName;
    }
}
