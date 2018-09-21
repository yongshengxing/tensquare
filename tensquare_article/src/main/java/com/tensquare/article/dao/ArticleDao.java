package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    /**
     * 点赞
     * @param articleId
     */
    @Modifying
    @Query(value = "UPDATE tb_article SET thumbup= thumbup + 1 WHERE id = ?1",nativeQuery = true)
    public void addThumbupNum(String articleId);


    /**
     * 文章审核
     * @param articleId
     */
    @Modifying
    @Query(value = "UPDATE tb_article set state = 1 WHERE id= ?",nativeQuery = true)
    public void examineInfo(String articleId);



}
