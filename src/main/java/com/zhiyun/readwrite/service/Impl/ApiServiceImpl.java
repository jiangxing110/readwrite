package com.zhiyun.readwrite.service.Impl;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.zhiyun.readwrite.config.RabbitCofig;
import com.zhiyun.readwrite.entity.SystemTask;
import com.zhiyun.readwrite.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @Title: ApiServiceImpl
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1314:29
 */
@Service("ApiService")
@Slf4j
public class ApiServiceImpl implements ApiService {

    @Resource
    private RabbitTemplate rabbitTemplate1;


    @Override
    public String sendMessage(SystemTask systemTask)throws Exception {

        rabbitTemplate1.setMandatory(true);

        //添加监听器获取返送失败的消息
        rabbitTemplate1.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("replyCode:" + replyCode);
                System.out.println("replyText:" + replyText);
                System.out.println("匹配队列失败,返回消息:" + message.toString());
            }
        });
        //rabbitTemplate1.convertAndSend(Constants.DF_SYSTEM_TASK_EXCHANGE_NAME,"com.df.qwqw",JSON.toJSONString(systemTask));
        /**
         * 配置备份交换机：如果路由键之前，消息路由到相应的队列里，失败就进入备份交换机绑定的队列
         *
         * /*message -> {
         *                     message.getMessageProperties().setExpiration(17 * 1000  + "");//过期数据
         *                     message.getMessageProperties().setPriority(systemTask.getId());//优先级
         *                     message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);//是否持久化
         *                     return message;
         *                 }*/

        rabbitTemplate1.convertAndSend(RabbitCofig.DL_EXCHANGE, "com.df.qwqwwwww", JSON.toJSONString(systemTask)
                , new MessagePostProcessor(){
                    @Override
                    public Message postProcessMessage(Message arg0) throws AmqpException
                    {
                        arg0.getMessageProperties().setPriority(systemTask.getId());
                        return arg0;
                }});


       /* ConnectionFactory factory = new ConnectionFactory();
        //ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();



        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2); //持久化消息
        builder.priority(systemTask.getId());//优先级
        //builder.expiration("30000");// 设置 TTL=60000ms
        AMQP.BasicProperties properties = builder.build();

        channel.basicPublish("DL_EXCHANGE", "KEY_R", false, properties,
                JSON.toJSONString(systemTask).getBytes());

        log.error("消息发送成功");
        channel.close();
        connection.close();*/
        return systemTask.toString();
    }
}
