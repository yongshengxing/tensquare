package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{


    @Query(value = "SELECT * FROM tb_problem WHERE id in (SELECT problemid FROM tb_pl WHERE labelid = ?1 ) ORDER BY replytime DESC",nativeQuery = true)
    public Page<Problem> findNewList(String labelid,Pageable pageable);

    /**
     * 热门问答列表 hotlist/{label}/{page}/{size}
     * @param labelid
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem WHERE id in (SELECT problemid FROM tb_pl WHERE labelid = ?1 ) ORDER BY reply DESC",nativeQuery = true)
    public Page<Problem> hotlist(String labelid,Pageable pageable);


    /**
     * 等待回答列表 waitlist/{label}/{page}/{size}
     * @param labelid
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem WHERE id in (SELECT problemid FROM tb_pl WHERE labelid = 1 ) AND reply = 0 ORDER BY createtime DESC",nativeQuery = true)
    public Page<Problem> waitlist(String labelid,Pageable pageable);

    /**
     * 查询所有问题 waitlist/{label}/{page}/{size}
     * @param labelid
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem WHERE id in (SELECT problemid FROM tb_pl WHERE labelid = 1 )  ORDER BY createtime DESC",nativeQuery = true)
    public Page<Problem> findAll(String labelid,Pageable pageable);

}
