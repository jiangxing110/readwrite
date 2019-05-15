package com.zhiyun.readwrite.utils;/**
 * @Title: Product
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1411:11
 */

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Classname Product
 * @Description TODO
 * @Date 2019/5/14 11:11
 * @Created by jiangxing
 */
public class Product {
    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为 5672

    public static void main(String[] args) throws IOException,
            TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();// 创建连接
        Channel channel = connection.createChannel();// 创建信道
        // 创建一个 type="direct" 、持久化的、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        //创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        //发送一条持久化的消息: hello world !
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put(" loca1tion", "here ");
        headers.put(" time ", " today");
        String message = "国家兴亡匹夫有责 !";
        /*channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
       上面这行代码发送了一条消息，这条消息的投递模式 del very mode 设直为 ，即消息会
被持久化(即存入磁盘)在服务器中。同时这条消息的优先级 priority )设置为 ont
//为" text/pl ain" 可以自己设定消息的属性:*/
      /* channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, new AMQP.BasicProperties().builder().contentType("text/p1ain").
                deliveryMode(2)
                .priority(1)
                .userId("jx")
                .headers(headers)
                .expiration(String.valueOf(10000))
                .build(),
                message.getBytes());*/
        /*channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, new AMQP.BasicProperties().builder()
                        .expiration(String.valueOf(10000))
                        .build(),
                message.getBytes());*/
        channel.basicPublish(EXCHANGE_NAME, "", true,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                "mandatory test".getBytes());
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                String message = new String(bytes);
                System.out.println("Basic.Return 返回的结果是: " + message);
            }
        });
        //解绑
        //channel.queueUnbind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        //关闭资源
        channel.close();
        connection.close();
    }
}
