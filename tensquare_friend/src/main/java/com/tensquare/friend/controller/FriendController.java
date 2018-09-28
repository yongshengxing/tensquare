package com.tensquare.friend.controller;

import com.tensquare.friend.client.FrientClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-28 19:27
 **/
@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    HttpServletRequest servletRequest;

    @Autowired
    private FrientClient frientClient;

    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriends(@PathVariable String friendid, @PathVariable String type) {

        //获取当前用户id
        Claims claims = (Claims) servletRequest.getAttribute("user_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足,请以User身份登录");
        }

        String usreId = claims.getId();
        if (usreId == null || "".equals(usreId)) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足,请以User身份登录");
        }

        //判断当前是添加还是移出好友  类型 1:喜欢 2：不喜欢
        if ("1".equals(type)) {
            //添加好友
          int flag = friendService.addFriend(usreId,friendid);
          if (flag == 1){
              frientClient.updateFansCount(1,usreId );//增加自己的关注数
              frientClient.updateFollowCount(1, friendid);//增加好友的粉丝数
              return new Result(true, StatusCode.OK, "添加成功");
          }else if(flag == 0){
              return new Result(false, StatusCode.ERROR, "不能重复添加");
          }
        } else if ("2".equals(type)) {
            //添加不喜欢好友
            int flag = friendService.addUnFriend(usreId,friendid);
            if (flag == 1){

                return new Result(true, StatusCode.OK, "添加成功");
            }else if(flag == 0){
                return new Result(false, StatusCode.ERROR, "不能重复添加");
            }
        } else {
            return new Result(false, StatusCode.REPERROR, "参数非法,需要1到2!");
        }


        return new Result(true, StatusCode.ERROR, "操作失败!");
    }

    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result deletFriend(@PathVariable String friendid){


        //获取当前用户id
        Claims claims = (Claims) servletRequest.getAttribute("user_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足,请以User身份登录");
        }

        String usreId = claims.getId();
        if (usreId == null || "".equals(usreId)) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足,请以User身份登录");
        }
        friendService.deletFriend(usreId,friendid);
        frientClient.updateFollowCount(-1,usreId );//减少自己的关注数
        frientClient.updateFansCount(-1,friendid );//减少好友粉丝数
        return new  Result(true, StatusCode.OK, "删除成功");
    }
}
