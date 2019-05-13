package com.zhiyun.readwrite.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
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

    /**
     * 配置交换机实例
     *
     * @return
     */
    @Bean
    public TopicExchange directExchange() {
        return new TopicExchange(Constants.DF_SYSTEM_TASK_EXCHANGE_NAME);
    }

    /**
     * 配置队列实例，并且设置持久化队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(Constants.DF_SYSTEM_TASK_QUEUE_NAME, true);
    }
    @Bean
    public Queue queue1() {
        return new Queue(Constants.DF_SYSTEM_TASK_QUEUE_NAME1, true);
    }
    /**
     * 将队列绑定到交换机上，并设置消息分发的路由键
     *
     * @return
     */
    @Bean
    public Binding binding() {
        //链式写法: 用指定的路由键将队列绑定到交换机
        return BindingBuilder.bind(queue()).to(directExchange()).with(Constants.DF_SYSTEM_TASK_QUEUE_ROUTE_KEY);
    }
    @Bean
    public Binding binding1() {
        //链式写法: 用指定的路由键将队列绑定到交换机
        return BindingBuilder.bind(queue1()).to(directExchange()).with(Constants.DF_SYSTEM_TASK_QUEUE_ROUTE_KEY1);
    }

}
