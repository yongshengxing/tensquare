package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    public User findByNickname(String nickName);

//    UPDATE tb_user set fanscount= fanscount + 1 WHERE id = 1
//UPDATE tb_user set followcount= followcount + 1 WHERE id = 1
    @Modifying
    @Query(value = " UPDATE tb_user set fanscount= fanscount + ? WHERE id = ?",nativeQuery = true)
    public void updateFansCount(int num,String id);

    @Modifying
    @Query(value = " UPDATE tb_user set followcount= followcount + ? WHERE id = ?",nativeQuery = true)
    public void updateFollowCount(int num,String id);

}
