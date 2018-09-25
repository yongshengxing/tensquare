package com.tensquare.serach.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-23 21:11
 **/
@RestControllerAdvice
public class SearchControllerException {

    @ExceptionHandler(value = Exception.class)
    public Result catchException(Exception exc){
        return new Result(false, StatusCode.ERROR,exc.getMessage());
    }

}
