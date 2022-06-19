package basePackage.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "basePackage")
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public DataSource dataSource(){
        try {
            DriverManagerDataSource manager= new DriverManagerDataSource();
            manager.setDriverClassName("org.postgresql.Driver");
            manager.setUrl("jdbc:postgresql:fruitsdb");
            manager.setUsername("postgres");
            manager.setPassword("Pflhjn23");
            return manager;
        }catch (Exception e){
//            logger.error("Embedded DataSource bean cannot be created ", e);
            e.printStackTrace();
            System.out.println("Embedded DataSource bean cannot be created " + e.getMessage());;
            return null;
        }
    }

    private Properties hibernateProperties(){
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        //spring.jpa.hibernate.ddl-auto=none увидел в примерах мб понадобится
        hibernateProperties.put("hibernate.format_sql", true);
        hibernateProperties.put("hibernate.use_sql_comments", true);
        hibernateProperties.put("hibernate.show_sql", true);
        hibernateProperties.put("hibernate.max_fetch_depth", 3);
        hibernateProperties.put("hibernate.jdbc.batch_size", 10);
        hibernateProperties.put("hibernate.jdbc.fetch_size", 50);
        return hibernateProperties;
    }

    @Bean
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setPackagesToScan("basePackage.entities");
        bean.setHibernateProperties(hibernateProperties());
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws IOException{
        return new HibernateTransactionManager(sessionFactory());
    }

}
