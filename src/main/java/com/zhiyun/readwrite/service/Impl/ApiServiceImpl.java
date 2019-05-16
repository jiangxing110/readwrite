package com.zhiyun.readwrite.service.Impl;

import com.alibaba.fastjson.JSON;
import com.zhiyun.readwrite.config.Constants;
import com.zhiyun.readwrite.entity.SystemTask;
import com.zhiyun.readwrite.service.ApiService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Title: ApiServiceImpl
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1314:29
 */
@Service("ApiService")
public class ApiServiceImpl implements ApiService {

    /*@Autowired
    private AmqpTemplate rabbitTemplate;*/
    @Resource
    private RabbitTemplate rabbitTemplate1;


    @Override
    public String sendMessage(SystemTask systemTask) {

       /* rabbitTemplate1.setMandatory(true);
        //添加监听器获取返送失败的消息
        rabbitTemplate1.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("replyCode:"+replyCode);
                System.out.println("replyText:"+replyText);
                System.out.println("匹配队列失败,返回消息:" + message.toString());
            }
        });*/
        //rabbitTemplate.convertAndSend(Constants.DF_SYSTEM_TASK_EXCHANGE_NAME,"com.df.qwqw",JSON.toJSONString(systemTask));
        /**
         * 配置备份交换机：如果路由键之前，消息路由到相应的队列里，失败就进入备份交换机绑定的队列
        * */

        rabbitTemplate1.convertAndSend(Constants.DF_SYSTEM_TASK_EXCHANGE_NAME,"com.df.qwqwwwww",JSON.toJSONString(systemTask)
                /*message ->{
                    // 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
                    message.getMessageProperties().setExpiration(1 * 1000 * 60 + "");
                    return message;
                }*/ );

        return systemTask.toString();
    }
}
