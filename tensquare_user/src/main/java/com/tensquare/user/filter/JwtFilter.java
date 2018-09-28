package com.tensquare.user.filter;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-27 13:58
 **/
@Component
public class JwtFilter implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;


    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        //无论如何都放行。具体能不能操作还是在具体的操作中去判断。
        //拦截器只是负责把头请求头中包含token的令牌进行一个解析验证。
        // 删除用户，必须拥有管理员权限，否则不能删除。
        //前后端约定：前端请求微服务时需要添加头信息Authorization ,内容为Bearer+空格  +token
        String authorization = request.getHeader("Authorization");
        if (authorization != null && "".equals(authorization) &&  authorization.startsWith("Bearer ")){
//            StringUtils.isEmpty(authorization);
           final String token = authorization.substring(new String("Bearer ").length());
            if (!token.isEmpty()){
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    if (!claims.isEmpty()){
                        if("admin".equals(claims.get("roles"))){//如果是管理员
                            request.setAttribute("admin_claims", claims);
                        } if("user".equals(claims.get("roles"))){//如果是用户
                            request.setAttribute("user_claims", claims);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不正确！");
                }
            }
        }
        return true;
    }
}
