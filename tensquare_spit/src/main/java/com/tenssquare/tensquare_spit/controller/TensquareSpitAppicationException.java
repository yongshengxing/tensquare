package com.tenssquare.tensquare_spit.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-22 19:16
 **/
@RestControllerAdvice
public class TensquareSpitAppicationException {

    @ExceptionHandler(value = Exception.class)
    public Result error(Exception ex){
        ex.printStackTrace();
        return new Result(false, StatusCode.ERROR,ex.getMessage());
    }

}
