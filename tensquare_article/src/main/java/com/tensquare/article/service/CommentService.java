package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-22 22:25
 **/
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao ;


    public void saveComment(Comment comment){

        comment.setPublishdate(new Date());


    }
}
