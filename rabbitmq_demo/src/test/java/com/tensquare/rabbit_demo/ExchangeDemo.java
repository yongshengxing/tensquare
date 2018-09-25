package com.tensquare.rabbit_demo;

import com.tensquare.rabbitmqdemo.RabbitDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-25 17:10
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitDemoApplication.class)
public class ExchangeDemo {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test1(){
        Map<String,String> map = new HashMap<>();
        map.put("username","张三" );
        map.put("mobile","123445" );
        rabbitTemplate.convertAndSend("testExchange","" ,map );
    }

    @Test
    public void testTopic(){

        Map<String,String> map = new HashMap<>();
        map.put("username","张三" );
        map.put("mobile","123445" );
//        rabbitTemplate.convertAndSend(, , );
//        rabbitTemplate.convertAndSend("topicDemo","goods.11111" , map);
        rabbitTemplate.convertAndSend("topicDemo","abdcx.log" , map);
//        rabbitTemplate.convertAndSend("topicDemo","goods.log" , map);
    }
}
