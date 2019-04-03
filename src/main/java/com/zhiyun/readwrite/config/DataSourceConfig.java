package com.zhiyun.readwrite.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Title: DataSourceConfig
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/4/29:16
 */

@Configuration
public class DataSourceConfig {
    @Bean(name = "selectDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource.read01") // application.properteis中对应属性的前缀
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "updateDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource.write") // application.properteis中对应属性的前缀
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }
}
