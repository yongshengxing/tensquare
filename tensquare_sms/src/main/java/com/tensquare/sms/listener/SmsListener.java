package com.tensquare.sms.listener;

import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-25 19:27
 **/
@RabbitListener(queues = "sms")
@Component
public class SmsListener {

    @Value("${aliyun.sms.template_code}")
    private String template_code;
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;

    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void smsListener(Map<String, String> map) {

        String mobile = map.get("userMobile");
        String code = map.get("randomCode");

//         sendSms(String mobile,String template_code,String sign_name,String param);

        try {
            smsUtil.sendSms(mobile, template_code, sign_name, "{\"code\":" + code + "}");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
