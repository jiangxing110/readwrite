package com.zhiyun.readwrite.service.Impl;

import com.alibaba.fastjson.JSON;
import com.zhiyun.readwrite.config.Constants;
import com.zhiyun.readwrite.entity.SystemTask;
import com.zhiyun.readwrite.service.ApiService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


        //rabbitTemplate.convertAndSend(Constants.DF_SYSTEM_TASK_EXCHANGE_NAME,"com.df.qwqw",JSON.toJSONString(systemTask));
        rabbitTemplate.convertAndSend(Constants.DF_SYSTEM_TASK_EXCHANGE_NAME,"com.df.qwqwwwww",JSON.toJSONString(systemTask));
        return systemTask.toString();
    }
}
