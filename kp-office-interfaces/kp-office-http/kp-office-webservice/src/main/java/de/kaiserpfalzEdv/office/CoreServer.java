package de.kaiserpfalzEdv.office;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "de.kaiserpfalzEdv.office"
)
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan(
        basePackages = "de.kaiserpfalzEdv.office",
        excludeFilters = {
                @ComponentScan.Filter(
                        value = Repository.class,
                        type = FilterType.ANNOTATION
                )
        }
)
@EnableConfigurationProperties
public class CoreServer  {
    public static void main(String[] args) {
        SpringApplication.run(CoreServer.class, args);
    }
}
