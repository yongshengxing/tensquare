package com.tenssquare.tensquare_spit.controller;

import com.tenssquare.tensquare_spit.pojo.Spit;
import com.tenssquare.tensquare_spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-22 19:50
 **/
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 保存
     *
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result saveSpitInfo(@RequestBody Spit spit) {
        spitService.saveInfo(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /**
     * Spit全部列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Spit> spitList = spitService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", spitList);
    }


    /**
     * {spitId}     根据ID查询吐槽
     *
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId) {
        Spit spit = spitService.findById(spitId);
        return new Result(true, StatusCode.OK, "查询成功", spit);
    }


    /**
     * {spitId}     修改吐槽
     *
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result findById(@PathVariable String spitId, @RequestBody Spit spit) {
        spit.set_id(spitId);
        spitService.updateInfo(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /**
     * {spitId}     根据ID删除吐槽
     *
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result deleteSpitInfo(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据上级ID查询吐槽数据（分页）
     *
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findPreIdByPage(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
        Page<Spit> spitPage = spitService.findPreIdByPage(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(spitPage.getTotalPages(), spitPage.getContent()));
    }

    /**
     * 点赞操作
     *
     * @param spitId
     * @return
     */
//    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result addThumbup(@PathVariable String spitId) {
        spitService.addThumbup(spitId);
        return new Result(true, StatusCode.OK, "点赞成功");
    }


    /**
     * 点赞操作
     *
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result addThumbup2(@PathVariable String spitId) {

        String userId = "dengliming";
        String res = (String) redisTemplate.opsForValue().get("thumbup_" + userId + "_" + spitId);
        if (res != null) {
            return new Result(false, StatusCode.ERROR, "你已点赞");
        }
        spitService.addThumbup2(spitId);
        redisTemplate.opsForValue().set("thumbup_" + userId + "_" + spitId, "1");
        return new Result(true, StatusCode.OK, "点赞成功");
    }



}
