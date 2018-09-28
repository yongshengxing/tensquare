package com.tensquare.qa.cilent;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-28 18:32
 **/
@FeignClient("tensquare-base")
public interface LableCilent {

    @RequestMapping(value = "/label/{id}",method = RequestMethod.GET)
    public Result findOne(@PathVariable("id") String id);

    @RequestMapping(value = "/label/",method = RequestMethod.GET)
    public Result findAll();

}
