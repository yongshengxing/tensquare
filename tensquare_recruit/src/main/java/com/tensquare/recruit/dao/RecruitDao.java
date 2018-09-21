package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    /**
     * 查找热门职位
     *
     * findTop6By 表示查找前6个  top前的意思
     *
     * @return
     */
    public List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);




    /**
     * 需求分析：查询状态不为0并以创建日期降序排序，查询前12条记录
     */
    public List<Recruit> findTop12ByStateIsNotOrderByCreatetimeDesc(String id);
}
