package com.tenssquare.tensquare_spit.service;

import com.tenssquare.tensquare_spit.dao.SpitDao;
import com.tenssquare.tensquare_spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import java.util.Date;
import java.util.List;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-22 19:33
 **/
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * spit 增加吐槽
     *
     * @param spit
     */
    public void saveInfo(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setShare(0);
        spit.setState("1");
        spit.setVisits(0);
        spit.setPublishtime(new Date());
        String parentid = spit.getParentid();
        if (parentid != null && !"".equals(parentid)){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query,update ,"spit" );
        }
        spitDao.save(spit);
    }

    /**
     * 查找所有
     *
     * @return
     */
    public List<Spit> findAll() {

        return spitDao.findAll();

    }

    /**
     * /spit/{spitId} 根据ID查询吐槽
     *
     * @param id
     * @return
     */
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }


    /**
     * spit 修改吐槽
     *
     * @param spit
     */
    public void updateInfo(Spit spit) {

        spitDao.save(spit);

    }

    /**
     * spit/{spitId}     根据ID删除吐槽
     *
     * @param id
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

  /*  public List<Spit> findByCondition(Spit spit){



    }*/


    public Page<Spit> findAllByPage(Spit spit, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Example<Spit> spitExample = null;

        return spitDao.findAll(spitExample, pageRequest);

    }

    /**
     * 查看吐槽分类
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findPreIdByPage(String parentid, int page, int size) {
        PageRequest spitPage = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid, spitPage);
    }


    /**
     * 点赞数 + 1    thumbup/{spitId}
     * @param id
     */
    public void addThumbup(String id){
        Spit spit = spitDao.findById(id).get();
        spit.setThumbup(spit.getThumbup() + 1);
        spitDao.save(spit);
    }


    /**
     * 使用模板添加添加点赞数
     * @param id
     */
    public void addThumbup2(String id){

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup",1 );
        mongoTemplate.updateFirst(query,update ,Spit.class );
    }
}
