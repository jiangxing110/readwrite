package com.zhiyun.readwrite;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@MapperScan("com.zhiyun.readwrite.dao")
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class ReadwriteApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ReadwriteApplication.class, args);
        String serverport = run.getEnvironment().getProperty("server.port");
        String serverName=run.getEnvironment().getProperty("server.servlet.context-path");
        System.err.println("项目启动于: http://localhost:" + serverport+serverName);
    }

}
