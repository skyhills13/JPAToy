package io.lilo.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/db.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"io.lilo.repository"})
@ComponentScan(basePackages = {"io.lilo.service"})
public class DBConfig {

    public final String SCANNED_PACKED_NAME = "io.lilo.domain";
    @Autowired
    Environment environment;

    private final String PROPERTY_KEY_DB_DRIVERCLASSNAME = "database.driverClassName";
    private final String PROPERTY_KEY_DB_URL = "database.url";
    private final String PROPERTY_KEY_DB_USERNAME = "database.username";
    private final String PROPERTY_KEY_DB_PASSWORD = "database.password";
    private final String PROPERTY_KEY_JPA_DIALECT = "hibernate.dialect";
    private final String PROPERTY_KEY_DB_FORMATSQL = "hibernate.format_sql";
    private final String PROPERTY_KEY_DB_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private final String PROPERTY_KEY_DB_SHOWSQL = "hibernate.show_sql";
    private final String PROPERTY_KEY_DB_JPATODDL = "hibernate.hbm2ddl.auto";

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {

        BasicDataSource dataSource = new BasicDataSource();

        String driverClassName = environment.getProperty(PROPERTY_KEY_DB_DRIVERCLASSNAME);
        String url = environment.getProperty(PROPERTY_KEY_DB_URL);
        String username = environment.getProperty(PROPERTY_KEY_DB_USERNAME);
        String password = environment.getProperty(PROPERTY_KEY_DB_PASSWORD);

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(SCANNED_PACKED_NAME);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

        String jpaDialect = PROPERTY_KEY_JPA_DIALECT;
        String jpaFormatSql = PROPERTY_KEY_DB_FORMATSQL;
        String jpaNamingStrategy = PROPERTY_KEY_DB_NAMING_STRATEGY;
        String jpaShowSql = PROPERTY_KEY_DB_SHOWSQL;
        String jpaOperationMode= PROPERTY_KEY_DB_JPATODDL;

        Properties jpaProperties = new Properties();
        jpaProperties.put(jpaDialect, environment.getProperty(jpaDialect));
        jpaProperties.put(jpaFormatSql, environment.getProperty(jpaFormatSql));
        jpaProperties.put(jpaNamingStrategy, environment.getProperty(jpaNamingStrategy));
        jpaProperties.put(jpaShowSql, environment.getProperty(jpaShowSql));
        jpaProperties.put(jpaOperationMode, environment.getProperty(jpaOperationMode));

        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory().getObject()
        );

        return transactionManager;
    }
}
