package com.tensquare.webmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-10-04 11:28
 **/
@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class WebManagerAppliction {
    public static void main(String[] args) {
        SpringApplication.run(WebManagerAppliction.class,args);
    }
}
