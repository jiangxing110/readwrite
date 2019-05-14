package com.zhiyun.readwrite.service.Impl;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP;
import com.zhiyun.readwrite.config.Constants;
import com.zhiyun.readwrite.entity.SystemTask;
import com.zhiyun.readwrite.service.ApiService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.amqp.rabbit.core.RabbitAdmin.QUEUE_NAME;

/**
 * @Title: ApiServiceImpl
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1314:29
 */
@Service("ApiService")
public class ApiServiceImpl implements ApiService {

    @Autowired
    private AmqpTemplate rabbitTemplate;



    @Override
    public String sendMessage(SystemTask systemTask) {
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().expiration("500").build();
        //senderChannel.basicPublish("", QUEUE_NAME, properties, message.getBytes("UTF-8"));

        //rabbitTemplate.convertAndSend(Constants.DF_SYSTEM_TASK_EXCHANGE_NAME,"com.df.qwqw",JSON.toJSONString(systemTask));
        rabbitTemplate.convertAndSend(Constants.DF_SYSTEM_TASK_EXCHANGE_NAME,"com.df.qwqwwwww",JSON.toJSONString(systemTask), (MessagePostProcessor) properties);
        return systemTask.toString();
    }
}
