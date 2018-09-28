package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.UnFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.UnFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-28 19:31
 **/
@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UnFriendDao unFriendDao;


    public int addFriend(String usreId, String friendid) {

        //先判断当前用户是否已添加该好友
        Friend dbFriend = friendDao.findByUseridAndFriendid(usreId, friendid);
        if (dbFriend != null) {
            return 0;
        }
        dbFriend = new Friend();
        dbFriend.setUserid(usreId);
        dbFriend.setFriendid(friendid);
        dbFriend.setIslike("0");
        friendDao.save(dbFriend);

        //判断当前用户好友类表是否包含该用户
        Friend friendHasUser = friendDao.findByUseridAndFriendid(friendid, usreId);
        if (friendHasUser != null) {
            //好友也包含当前用户
            friendDao.updateFriendIsLikeStatus("1", usreId, friendid);
            friendDao.updateFriendIsLikeStatus("1", friendid, usreId);

        }

        return 1;
    }

    public int addUnFriend(String usreId, String friendid) {

        //查询当前用户是否已经添加了该好友到不喜欢列表
        UnFriend dbUnFriend = unFriendDao.findByUseridAndFriendid(usreId, friendid);
        if (dbUnFriend != null) {
            return 0;
        }

        UnFriend friend = new UnFriend();
        friend.setFriendid(friendid);
        friend.setUserid(usreId);
        unFriendDao.save(friend);
        return 1;
    }

    public void deletFriend(String usreId, String friendid) {
        UnFriend byUseridAndFriendid = unFriendDao.findByUseridAndFriendid(usreId, friendid);
        if (byUseridAndFriendid != null) {
            throw new RuntimeException("你已删除该用户,请勿重复操作");
        }
        friendDao.deleteByUseridAndFriendid(usreId,friendid );
        //删除,好友列表显示为单向
        friendDao.updateFriendIsLikeStatus("0",friendid,usreId );
        //添加到非好友
        this.addUnFriend(usreId,friendid );

    }
}
