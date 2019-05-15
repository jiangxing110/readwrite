package com.zhiyun.readwrite.utils;

import com.rabbitmq.client.*;
import sun.awt.CustomCursor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Title: RabbitConsumer
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1411:19
 */

public class RabbitConsumer {
    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;

    /*public static void main(String[] args) throws IOException,
            TimeoutException, InterruptedException {
        Address[] addresses = new Address[]{
                new Address(IP_ADDRESS, PORT)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        //这里的连接方式与生产者的 demo 略有不同 注意辨别区别
        Connection connection = factory.newConnection(addresses);
        final Channel channel = connection.createChannel();
        channel.basicQos(64); // 设置客户端最多接收未被 ack 的消息的个数
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(" recv message: " + new String(body));

                channel.basicAck(envelope.getDeliveryTag(), false);

            }
        };
        channel.basicConsume(QUEUE_NAME, consumer);
        //等待回调函数执行完毕之后 关闭资源
        channel.close();
        connection.close();
    }*/
}