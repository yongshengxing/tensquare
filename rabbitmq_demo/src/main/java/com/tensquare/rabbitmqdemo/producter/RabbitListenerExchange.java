package com.tensquare.rabbitmqdemo.producter;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-25 17:09
 **/
@RabbitListener(queues = "tom")
@Component
public class RabbitListenerExchange {
    @RabbitHandler
    public void listen(Map<String,String> map){
        System.out.println("tom接收到的用户名为: " + map.get("username"));
        System.out.println("tom接收到的手机号为: " + map.get("mobile"));
    }
}
