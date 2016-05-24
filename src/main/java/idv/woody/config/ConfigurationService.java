package idv.woody.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by chun-chiao on 2016/5/8.
 */
@Service
@Scope("singleton")
public class ConfigurationService {

    @Value("${lab.infinispan.configFileName:lab-cache.xml}")
    private String infinispanConfigName;

    @Value("${lab.jgroup.configFileName:jgroups.xml}")
    private String jgroupConfigName;

    @Value("${lab.jgroup.clusterName:labCluster}")
    private String clusterName;

    public String getInfinispanConfigName() {
        return infinispanConfigName;
    }

    public String getJgroupConfigName() {
        return jgroupConfigName;
    }

    public String getClusterName() {
        return clusterName;
    }
}
