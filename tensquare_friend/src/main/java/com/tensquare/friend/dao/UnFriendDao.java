package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.UnFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-28 19:27
 **/
public interface UnFriendDao extends JpaRepository<UnFriend,String> {

    public UnFriend findByUseridAndFriendid(String usreId, String friendid);


}
