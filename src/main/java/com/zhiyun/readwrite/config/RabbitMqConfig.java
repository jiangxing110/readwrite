package com.zhiyun.readwrite.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: RabbitMqConfig
 * @ProjectName: readwrite
 * @Description: rabbitMq配置
 * @author: jiangxing
 * @date 2019/5/1115:17
 */
@Configuration
@Slf4j
public class RabbitMqConfig {

    @Bean
    public Queue Queue() {
        return new Queue("SystemTask");
    }

}
