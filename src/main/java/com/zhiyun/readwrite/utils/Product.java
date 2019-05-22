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
    public static final String ip = "127.0.0.1";
    public static final int port = 5672;
    public static final String username = "guest";
    public static final String password = "guest";

    public static void main(String[] arggs) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPassword(password);
        connectionFactory.setUsername(username);
        connectionFactory.setPort(port);
        connectionFactory.setHost(ip);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //create exchange
        channel.exchangeDeclare("exchange_priority","direct",true);

        //create queue with priority
        Map<String,Object> args = new HashMap<String,Object>();
        args.put("x-max-priority", 10);
        channel.queueDeclare("queue_priority", true, false, false, args);
        channel.queueBind("queue_priority", "exchange_priority", "rk_priority");

        //send message with priority
        for(int i=0;i<10;i++) {
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            if(i%2!=0){
                builder.priority(5);}
            AMQP.BasicProperties properties = builder.build();
            channel.basicPublish("exchange_priority","rk_priority",properties,("messages-"+i).getBytes());
        }

        channel.close();
        connection.close();
    }

}
