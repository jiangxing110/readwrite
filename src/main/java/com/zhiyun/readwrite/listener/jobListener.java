package com.zhiyun.readwrite.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.zhiyun.readwrite.entity.SystemTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.zhiyun.readwrite.config.Constants.DF_SYSTEM_TASK_QUEUE_NAME;
import static com.zhiyun.readwrite.config.Constants.DF_SYSTEM_TASK_QUEUE_NAME1;

/**
 * @Title: jobListener
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1116:36
 */
@Component
@Slf4j
@RabbitListener(queues = DF_SYSTEM_TASK_QUEUE_NAME)
public class jobListener {
    @RabbitHandler
    public void process(String hello, Channel channel, Message message) throws IOException {
        System.out.println("HelloReceiver收到 : " + hello +message+ "收到时间" + new Date());
        try {
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发

           // channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("receiver success");
        } catch (Exception e) {
            e.printStackTrace();
       //丢弃这条消息
          channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            System.out.println("receiver fail");
        }
    }

}
