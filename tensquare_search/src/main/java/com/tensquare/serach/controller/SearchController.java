package com.tensquare.serach.controller;

import com.tensquare.serach.pojo.Article;
import com.tensquare.serach.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-23 21:09
 **/
@RestController
@RequestMapping("/article")
public class SearchController {

    @Autowired
    private ArticleService articleService;


    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article) {
        articleService.saveArticle(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    @RequestMapping(value = "/search/{keywords}/{page}/{size}", method =
            RequestMethod.GET)
    public Result findByTitleLike(@PathVariable String keywords,
                                  @PathVariable int page, @PathVariable int size) {
        Page<Article> articlePage = articleService.findInfoByPage(keywords, page, size);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Article>(articlePage.getTotalPages(), articlePage.getContent()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll( ) {
        List<Article> articleList = articleService.findAll();
        return new Result(true, StatusCode.OK, "查询成功",articleList );
    }
}
