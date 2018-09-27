package com.tensquare.qa.config;

import com.tensquare.qa.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-27 14:37
 **/
@Configuration
public class QaJwpInterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtFilter jwtFilter;

    protected void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器要声明拦截器对象和要拦截的请求
        registry.addInterceptor(jwtFilter)
                .addPathPatterns("/**")//表示拦截所有请求
                .excludePathPatterns("/**/login/**");//表示放行资源
    }


}
