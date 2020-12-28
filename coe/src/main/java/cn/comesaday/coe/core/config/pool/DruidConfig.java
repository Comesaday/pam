package cn.comesaday.coe.core.config.pool;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-16 15:40
 */
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DruidConfig {

    @Autowired
    private DataSourceProperties properties;

    @Bean
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(properties.getUrl());
        datasource.setUsername(properties.getUsername());
        datasource.setPassword(properties.getPassword());
        datasource.setDriverClassName(properties.getDriverClassName());
        return datasource;
    }

    @Bean(name = "jdbcTemple")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}