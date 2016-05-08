package idv.woody.factory;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by chun-chiao on 2016/5/8.
 */
@Configuration
@Scope("singleton")
public class PropertyFactory {
    @Bean(name = "config")
    public PropertiesFactoryBean config() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("lab.properties"));
        return bean;
    }
}
