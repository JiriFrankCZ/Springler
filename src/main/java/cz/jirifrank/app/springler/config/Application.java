package cz.jirifrank.app.springler.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

/**
 * Main configuration file of app
 *
 * @author Jiří Frank
 */

@EnableAsync
@EnableScheduling
@EnableJpaRepositories(basePackages = Application.APPLICATION_PACKAGE + ".repository")
@EnableElasticsearchRepositories(basePackages = Application.APPLICATION_PACKAGE + ".repository")
@SpringBootApplication(scanBasePackages = Application.APPLICATION_PACKAGE)
@EntityScan(basePackages = Application.APPLICATION_PACKAGE + ".model.entity")
public class Application {

	public static final String APPLICATION_PACKAGE = "cz.jirifrank.app.springler";

	public static void main(String[] args) {

		//SpringApplication.run(Application.class, args);
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

}
