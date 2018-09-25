package com.tensquare.serach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import utils.IdWorker;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-23 21:07
 **/
@SpringBootApplication
public class SerachApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerachApplication.class,args);
    }


    @Bean
    public IdWorker getIdWorker(){
        return  new IdWorker(1,1);
    }

}
