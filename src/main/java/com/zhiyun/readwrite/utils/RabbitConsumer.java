package com.zhiyun.readwrite.utils;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import sun.awt.CustomCursor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Title: RabbitConsumer
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1411:19
 */
@Slf4j
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
    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        //ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // DLX
        channel.exchangeDeclare("exchange.dlx", "direct", true);
        channel.exchangeDeclare("exchange.normal", "fanout", true);

        Map<String, Object> arg = new HashMap<String, Object>();
        // 设置DLX
        arg.put("x-dead-letter-exchange", "exchange.dlx");
        arg.put("x-dead-letter-routing-key", "routingkey.dlx");
        // 设置消息过期时间，消息过期后，会重新发布到DLX
        arg.put("x-message-ttl", 5000);
        channel.queueDeclare("queue.normal", true, false, false, null);
        //	死信队列
        channel.queueDeclare("queue.dlx", true, false, false, arg);

        channel.queueBind("queue.normal", "exchange.dlx", "aaac");
        channel.queueBind("queue.dlx", "exchange.dlx", "routingkey.dlx");

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2); //持久化消息
        builder.expiration("30000");// 设置 TTL=60000ms
        AMQP.BasicProperties properties = builder.build();

        channel.basicPublish("exchange.dlx", "routingkey.dlx", false, properties,
                "Message-5s".getBytes());

        log.error("消息发送成功");
        channel.close();
        connection.close();


    }
}