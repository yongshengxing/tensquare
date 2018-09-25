package com.tensquare.serach.service;

import com.tensquare.serach.dao.ArticleDao;
import com.tensquare.serach.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import java.util.ArrayList;
import java.util.List;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-23 21:45
 **/
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 添加
     * @param article
     */
    public void saveArticle(Article article){
        article.setId(idWorker.nextId()+"");
        articleDao.save(article);
    }

    /**
     * 查找所有
     * @return
     */
    public List<Article> findAll(){
        List<Article> articles = new ArrayList<>();

        Iterable<Article> all = articleDao.findAll();
        for (Article article : all) {
            articles.add(article);
        }
        return articles;
    }


    public Page<Article> findInfoByPage(String key,int page,int size){

        Pageable pageable = PageRequest.of(page - 1,size );
       return articleDao.findByContentOrTitleLikeOrTypeLike(key,key ,key ,pageable );

    }


}
