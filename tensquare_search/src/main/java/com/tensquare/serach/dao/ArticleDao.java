package com.tensquare.serach.dao;

import com.tensquare.serach.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-23 21:43
 **/
public interface ArticleDao extends ElasticsearchRepository<Article,String> {

    public Page<Article> findByContentOrTitleLikeOrTypeLike(String content, String title, String type,Pageable request);

}
