package com.tensquare.common;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-27 12:00
 **/
public class TestJwt {

    @Test
    public void testJwt1(){

        JwtBuilder builder = Jwts.builder().setId("123456")
                .setSubject("dengliming")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"dsx" );
        System.out.println(builder.compact());

    }

    public static void main(String[] args) {

        JwtBuilder builder = Jwts.builder()
                .setId("123456")//设置id
                .setSubject("dengliming")//用户名
                .setIssuedAt(new Date())//创建时间
                .setExpiration(new Date(new Date().getTime()+600000)) //过期时间
                .signWith(SignatureAlgorithm.HS256,"dlms" )//加密方式,和自定义盐,长度> 3
                .claim("role", "admin");//自定权限角色,多个角色可以使用map
        System.out.println(builder.compact());
    }

}
