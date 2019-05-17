package com.zhiyun.readwrite.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
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
    @RabbitListener(queues = "REDIRECT_QUEUE")
    public void onMessage(Message message, Channel channel) throws Exception {
        String messageContent = new String(message.getBody(), StandardCharsets.UTF_8);
        try {
            log.info("messageContent: " + messageContent);

            //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
           /* 其中 deliveryTag 可以看作消息的编号 ，它是 一个 64 位的长整型值，最大值是
            9223372036854775807 。如果 requeue 参数设置为 true ，则 RabbitMQ 会重新将这条消息存入
            队列，以便可以发送给下 个订阅的消费者;如果 requeue 参数设置为 false ，则 RabbitMQ
            立即会把消息从队列中移除，而不会把它发送给新的消费者。*/
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("监听器消息转换异常");
        }
    }
    @Override
    public void onMessage(Message message) {

    }
}
