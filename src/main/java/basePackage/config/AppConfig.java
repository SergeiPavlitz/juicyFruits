package basePackage.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "basePackage")
@EnableJpaRepositories(basePackages = {"basePackage.repos"})
@EnableTransactionManagement
public class AppConfig {

}
