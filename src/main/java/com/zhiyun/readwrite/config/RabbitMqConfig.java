package com.zhiyun.readwrite.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @Title: RabbitMqConfig
 * @ProjectName: readwrite
 * @Description: rabbitMq配置
 * @author: jiangxing
 * @date 2019/5/1115:17
 */
@Slf4j
@Configuration
public class RabbitMqConfig {
    /**
     * 备份交换机
     * */
    @Bean
    public FanoutExchange unrouteExchange(){
        return new FanoutExchange("unrouteExchange",true,false);
    }
    /**
     * 创建备份交互器与备份交互器队列
     */
    @Bean
    public Queue unrouteQueue(){
        return new Queue("unrouteQueue",true);
    }
    /**
     * 绑定备份交互器与备份队列,不需要指定key
     * */
    @Bean
    Binding bindingUnRouteExchangeMessages() {
        return BindingBuilder.bind(unrouteQueue()).to(unrouteExchange());
    }
    /**
     * 配置交换机实例
     *
     * @return
     */
    @Bean
    public TopicExchange directExchange() {
        /*备份交换机
        Map<String, Object> args = new HashMap<>();
        args.put("alternate-exchange", "unrouteExchange");*/
        return new TopicExchange(Constants.DF_SYSTEM_TASK_EXCHANGE_NAME,true,false);
    }

    /**
     * 配置队列实例，并且设置持久化队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        Map<String,Object>map=new HashMap<String, Object>();
        map.put("x-max-priority", 10);
        return new Queue(Constants.DF_SYSTEM_TASK_QUEUE_NAME, true,false,false,map);
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
