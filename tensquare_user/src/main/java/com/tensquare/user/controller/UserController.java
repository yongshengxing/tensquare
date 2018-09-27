package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>((int) pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
   /*     // 删除用户，必须拥有管理员权限，否则不能删除。
        //前后端约定：前端请求微服务时需要添加头信息Authorization ,内容为Bearer+空格  +token
        //获取请求头
        String authorization = request.getHeader("Authorization");
        if (authorization.isEmpty()){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }

        if (!authorization.startsWith("Bearer ")){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        String token = authorization.substring(new String("Bearer ").length());
        Claims claims = jwtUtil.parseJWT(token);
        String roles = (String) claims.get("roles");
        if (!roles.equals("admin")){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }*/

        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据手机号码发送验证码
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
    public Result sendMessage(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "短信验证码发送成功");
    }


    /**
     * 用户添加
     *
     * @param user
     * @param code
     * @return
     */
    @RequestMapping(value = "register/{code}", method = RequestMethod.POST)
    public Result addByCode(@RequestBody User user, @PathVariable String code) {
/*        //验证手机号码
       boolean flag = userService.checkCode(code,user.getMobile());
       if (flag){
        userService.add(user);
        return new Result(true,StatusCode.OK,"增加成功");

       }
        return new Result(true,StatusCode.ERROR,"验证码不存在或已过期");*/

        try {
            String randomCode = (String) redisTemplate.opsForValue().get("randomCode_" +user.getMobile());
            if (randomCode.isEmpty()) {
                return new Result(false, StatusCode.ERROR, "验证码不存或已过期");
            }
            if (!randomCode.equals(code)) {
                return new Result(false, StatusCode.ERROR, "验证码不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "验证码不不正确或已过期");
        }

        userService.add(user);
        //删除缓冲注册码信息
        redisTemplate.delete("randomCode_" +user.getMobile());
        return new Result(true, StatusCode.OK, "增加成功");
    }


    /**
     * 登录查询
     *
     * @param user
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result findByName(@RequestBody User user) {
        User dbUser = userService.findByUserName(user.getNickname(), user.getPassword());
        if (dbUser != null) {
            String token = jwtUtil.createJWT(dbUser.getId(), dbUser.getNickname(), "user");
            Map<String,Object> map = new HashMap<>();
            map.put("user_role","user" );
            map.put("token", token);
            map.put("name",user.getNickname());//昵称
            map.put("avatar",user.getAvatar());//头像
            return new Result(true, StatusCode.OK, "登录成功", map);
        }
        return new Result(false, StatusCode.LOGINERROR, "登录失败");
    }


}
