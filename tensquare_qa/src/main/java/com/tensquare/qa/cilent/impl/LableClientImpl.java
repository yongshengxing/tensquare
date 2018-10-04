package com.tensquare.qa.cilent.impl;

import com.tensquare.qa.cilent.LableCilent;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-10-04 0:34
 **/
@Component
public class LableClientImpl implements LableCilent{
    @Override
    public Result findOne(String id) {
        return new Result(false, StatusCode.ERROR,"熔断器启动");
    }

    @Override
    public Result findAll() {
        return new Result(false, StatusCode.ERROR,"熔断器启动");
    }
}
