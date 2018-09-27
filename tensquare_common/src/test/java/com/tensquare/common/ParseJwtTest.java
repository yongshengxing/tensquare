package com.tensquare.common;

import io.jsonwebtoken.*;

import java.text.SimpleDateFormat;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-27 12:18
 **/
public class ParseJwtTest {

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTYiLCJzdWIiOiJkZW5nbGltaW5nIiwiaWF0IjoxNTM4MDIyMjEyLCJleHAiOjE1MzgwMjI4MTIsInJvbGUiOiJhZG1pbiJ9.ZZmoop69lmk44nAmhmd6TiaT2X2i1ftpPH7KyniFhKw";

        try {
            Jws<Claims> dlms = Jwts.parser().setSigningKey("dlms").parseClaimsJws(token);
            Claims claims = dlms.getBody();
            System.out.println("用户名id:" + claims.getId());
            System.out.println("用户名:" + claims.getSubject());
            System.out.println("生效时间:" + new SimpleDateFormat("yyyy-MM-dd HH : mm : ss").format(claims.getIssuedAt()));
            System.out.println("过期时间:" + new SimpleDateFormat("yyyy-MM-dd HH : mm : ss").format(claims.getExpiration()));
            System.out.println("用户权限:" + claims.get("role"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
