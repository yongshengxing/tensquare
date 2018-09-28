package com.tensquare.friend.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-28 19:40
 **/
@RestControllerAdvice
public class FriendException {

    @ExceptionHandler(value = Exception.class)
    public Result catchException(Exception ex){
        return new Result(false, StatusCode.ERROR,ex.getMessage());
    }

}
