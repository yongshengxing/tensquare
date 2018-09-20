package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-19 19:03
 **/
@RestController
@RequestMapping("/label")
@CrossOrigin //表示允许跨域
public class LabelController {

    @Autowired
    private LabelService service;


    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        System.out.println(service);
        service.save(label);
        return new Result(StatusCode.OK,true,"保存成功!");
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        int i = 1 / 1 ;
        System.out.println(service);
        List<Label> labelList = service.findAll();
        return new Result(StatusCode.OK,true,"查询成功",labelList);
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findOne(@PathVariable String id){
        Label label = service.findOne(id);

        return new Result(StatusCode.OK,true,"查询成功",label);

    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteInfo(@PathVariable String id){
        service.delete(id);
        return new Result(StatusCode.OK,true,"删除成功");
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result updateInfo(@PathVariable String id,@RequestBody Label label){
        label.setId(id);
        service.update(label);
        return new Result(StatusCode.OK,true,"删除成功");
    }


}
