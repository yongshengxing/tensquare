package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-28 19:26
 **/
public interface FriendDao extends JpaRepository<Friend,String> {

    /**
     * 判断当前用户是否包含好友
     * @param userId
     * @param friendid
     * @return
     */
    public Friend findByUseridAndFriendid(String userId, String friendid);

    @Modifying
    @Query(value = "UPDATE tb_friend set islike = ? WHERE userid = ? and friendid = ?",nativeQuery = true)
    public void updateFriendIsLikeStatus(String status,String userId, String friendid );


    public void deleteByUseridAndFriendid(String usreId, String friendid);

}
