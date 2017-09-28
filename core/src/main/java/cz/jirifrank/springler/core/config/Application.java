package cz.jirifrank.springler.core.config;

//import de.codecentric.boot.admin.config.EnableAdminServer;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Main configuration file of app
 *
 * @author Jiří Frank
 */

//@EnableAsync
//@EnableScheduling
////@EnableJpaRepositories(basePackages = Application.APPLICATION_PACKAGE + ".repository")
//@SpringBootApplication(scanBasePackages = Application.APPLICATION_PACKAGE)
//@EntityScan(basePackages = Application.APPLICATION_PACKAGE + ".model.entity")
////@EnableAdminServer
//public class Application {
//
//	public static final String APPLICATION_PACKAGE = "cz.jirifrank.app.springler";
//
//	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//	}
//
//	@Bean
//	@Primary
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DataSource dataSource() {
//		return DataSourceBuilder.create().build();
//	}
//
//}


@SpringBootApplication
//@EnableAdminServer
@EnableEurekaClient
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RequestMapping("/greeting")
	public String greeting() {
		return "Hello from EurekaClient!";
	}
}
