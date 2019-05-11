package com.zhiyun.readwrite.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Title: jobListener
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1116:36
 */
@Component
@Slf4j
public class jobListener implements ChannelAwareMessageListener {


    @Override
    @RabbitListener(queues = "SystemTask")
    public void onMessage(Message message, Channel channel) throws Exception {
        String messageContent = new String(message.getBody(), StandardCharsets.UTF_8);
        try {
            log.error("messageContent: " + messageContent);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("监听器消息转换异常");
        }
    }
    @Override
    public void onMessage(Message message) {

    }
}
