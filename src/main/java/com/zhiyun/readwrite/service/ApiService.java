package com.zhiyun.readwrite.service;

import com.zhiyun.readwrite.entity.SystemTask;

/**
 * @Title: ApiService
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1314:30
 */
public interface ApiService  {
    /**
     * @Description: rabbitMq 发送信息
     * @Param: [systemTask]
     * @return: java.lang.String
     * @Author: jiangxing
     * @Date: 2019/5/13 14:43
     */
    String sendMessage(SystemTask systemTask);
}
