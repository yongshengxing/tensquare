package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-10-04 12:38
 **/
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        //表示过滤器执行的先后,
        // pre 表示方法执行前拦截 post表示执行后拦截
        return "pre";
    }

    @Override
    public int filterOrder() {
        //过滤器执行顺序,数字越小,越优先执行
        //存在多个过滤器,只需要修改返回数字大小
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否开启过滤器拦截 false 表示的是不启用
        //true 表示启用
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //拦截执行的方法
        //返回任意的obj对象表示 继续执行方法
        System.out.println("经过manager过滤器");

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String method = request.getMethod();
        //过滤器自带方法放行
        if ("OPPTION".equals(method)){
            return null;
        }
        //表示登录方法放行
        if (request.getRequestURI().indexOf("login") > 0){
            return null;
        }
        //获取请求头
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bean")){

            String token = authorization.substring(new String("Bean ").length());
            if (!StringUtils.isEmpty(token)){
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    if (claims != null){
                        String roles = (String) claims.get("roles");
                        if ("admin".equals(roles)){
                            //admin 角色放行
                            currentContext.addZuulRequestHeader("Authorization", authorization);
                            return null;
                        }
                    }
                } catch (Exception e) {
                    currentContext.setSendZuulResponse(false);
                    e.printStackTrace();
                }
            }
        }

        //终止运行
        currentContext.setSendZuulResponse(false);
        //无权限访问
        currentContext.setResponseStatusCode(403);
        currentContext.setResponseBody("无权访问");
        currentContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
