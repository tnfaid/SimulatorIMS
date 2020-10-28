package com.firstwap.ImitraAgentSimulatorV2;

import com.firstwap.ImitraAgentSimulatorV2.Repository.DataRepositoryBean;
import com.firstwap.ImitraAgentSimulatorV2.Repository.IDataRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@PropertySource(value = {"classpath:application.properties"})
@Configuration
public class DbConfiguration {

    @Bean(name = "deliveryDataSource")
    DataSource initSmsDataSource(@Value("${db.maxPoolsize.dr}") int poolSize,
                                 @Value("${db.username.dr}") String username,
                                 @Value("${db.password.dr}") String password,
                                 @Value("${db.url.dr}") String url,
                                 @Value("${spring.datasource.driver-class-name}") String driverClass,
                                 @Value("${db.leakDetectionThreshold}") int leakDetectionThreshold,
                                 @Value("${db.connectionTimeout}") int connectionTimeout,
                                 @Value("${db.connectionMaxLifetime}") int connectionMaxLifetime) {

        String namepool = "deliverystatus-db-connection-pool";

        final HikariDataSource hikariDataSource = init(poolSize, username, password, url,
                driverClass, leakDetectionThreshold,
                connectionTimeout, connectionMaxLifetime, namepool);
        return hikariDataSource;

    }

    HikariDataSource init(int poolSize, String username, String password,
                          String url, String driverClass, int leakDetectionThreshold,
                          int connectionTimeout, int connectionMaxLifetime, String namePool) {

        final HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(poolSize);
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setDriverClassName(driverClass);
        config.setLeakDetectionThreshold(leakDetectionThreshold * 1000);
        config.setConnectionTimeout(connectionTimeout * 1000);
        config.setMaxLifetime((connectionMaxLifetime * 60) * 1000);
        config.setPoolName(namePool);

        return new HikariDataSource(config);
    }

    @Bean
    IDataRepository initAgentDao(@Qualifier(value = "deliveryDataSource") DataSource cdrDataSources) {
        return new DataRepositoryBean(cdrDataSources);
    }

}
