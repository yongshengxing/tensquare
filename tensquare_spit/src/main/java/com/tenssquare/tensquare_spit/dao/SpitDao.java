package com.tenssquare.tensquare_spit.dao;

import com.tenssquare.tensquare_spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-22 19:33
 **/
public interface SpitDao extends MongoRepository<Spit,String> {

    public Page<Spit> findByParentid(String pid, Pageable pageable);

}
