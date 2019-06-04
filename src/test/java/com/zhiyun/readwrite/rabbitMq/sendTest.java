package com.zhiyun.readwrite.rabbitMq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * @Title: sendTest
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1115:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class sendTest {

    @Autowired
    private static RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpTemplate rabbit1Template;



    @Test
    public void demo1(){
        String mesg="国难当头，匹夫有责";
        rabbit1Template.convertAndSend("SystemTask", mesg);
        log.error(mesg);
    }
}
