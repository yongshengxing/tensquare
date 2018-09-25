package com.tensquare.rabbit_demo;

import com.tensquare.rabbitmqdemo.RabbitDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-25 16:38
 **/
@SpringBootTest(classes = RabbitDemoApplication.class)
@RunWith(SpringRunner.class)
public class DirectDemo {

    @Autowired
    private RabbitTemplate rabbitTemplate ;


    @Test
    public void sendMsg(){
        rabbitTemplate.convertAndSend("dengliming", "测试消息2");
    }

}
