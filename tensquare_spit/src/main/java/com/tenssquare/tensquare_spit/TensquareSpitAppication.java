package com.tenssquare.tensquare_spit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import utils.IdWorker;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-22 19:13
 **/
@SpringBootApplication
public class TensquareSpitAppication {

    public static void main(String[] args) {
        SpringApplication.run(TensquareSpitAppication.class,args);
    }


    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker(1,1);
    }
}
