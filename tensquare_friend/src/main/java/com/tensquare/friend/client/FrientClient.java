package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-28 19:24
 **/
@FeignClient(value = "tensquare-user")

public interface FrientClient {


    /**
     * 新增关注
     * @param num
     * @param id
     */
    @RequestMapping(value = "/user/updateFansCount/{num}/{id}",method = RequestMethod.GET)
    public void updateFansCount(@PathVariable("num") int num, @PathVariable("id") String id);

    /**
     * 新增粉丝
     * @param num
     * @param id
     */
    @RequestMapping(value = "/user/updateFollowCount/{num}/{id}",method = RequestMethod.GET)
    public void updateFollowCount(@PathVariable("num") int num, @PathVariable("id") String id) ;

}
