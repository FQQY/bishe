package com.example.springbootproject.service;

import com.example.springbootproject.dao.*;
import com.example.springbootproject.entity.Notice;
import com.example.springbootproject.entity.User;
import com.example.springbootproject.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-03-11-15:45
 */
@Service
public class UpdateService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private FavoriteBagMapper favoriteBagMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private PlayListMapper playListMapper;
    @Autowired
    private WorkMapper workMapper;

    public int updateNotice(Notice notice) {
        return noticeMapper.updateByNotId(notice);
    }

    public User updateUserInfo(User user) {
        if("0".equals(user.getBanFlag())) {
            //解封
            // 恢复该用户的所有作品
            workMapper.clearResumeWorksByUsrId(user.getUsrId(),"0");
            // 恢复该用户的历史记录
            historyMapper.clearResumeHistoryByUsrId(user.getUsrId(), "0");
            // 恢复该用户的收藏和收藏夹
            favoriteMapper.clearResumeFavoriteByUsrId(user.getUsrId(),"0");
            favoriteBagMapper.clearResumeFavoriteBagByUsrId(user.getUsrId(),"0");
            // 恢复该用户的稍后播放
            playListMapper.clearResumePlayListByUsrId(user.getUsrId(),"0");
            // 恢复用户的评论
            commentMapper.clearResumeCommentByUsrId(user.getUsrId(),"0");
            // 但是之前其他用户对你之前视频的收藏 历史记录 稍后播放都恢复不了

        } else {
            //封禁
            // 删除该用户的所有作品
            workMapper.clearResumeWorksByUsrId(user.getUsrId(),"1");
            // 删除该用户的历史记录
            historyMapper.clearResumeHistoryByUsrId(user.getUsrId(), "1");
            // 删除该用户的收藏和收藏夹
            favoriteMapper.clearResumeFavoriteByUsrId(user.getUsrId(),"1");
            favoriteBagMapper.clearResumeFavoriteBagByUsrId(user.getUsrId(),"1");
            // 删除该用户的稍后播放
            playListMapper.clearResumePlayListByUsrId(user.getUsrId(),"1");
            // 删除用户的评论
            commentMapper.clearResumeCommentByUsrId(user.getUsrId(),"1");
        }

        if(userMapper.updateByPrimaryKeySelective(user) > 0){
            return userMapper.selectByPrimaryKey(user.getUsrId());
        }
        return null;
    }
    public int updateWorkPassStatus(Video video) {
        return workMapper.updateWorkPassStatus(video);
    }

    public boolean agreeUserAsk(String usrId) {
        return userMapper.updateUsrAuthority(usrId) > 0;
    }

    public boolean refuseUserAsk(User user) {
        user.setAskFlag("0");
        return userMapper.updateAskFlag(user) > 0;
    }

    public User sendUserAsk(User user) {
        // 查看用户当前权限是否是审核员 是返回null
        User user1 = userMapper.selectByPrimaryKey(user.getUsrId());
        if("1".equals(user1.getUsrAuthority())) {
            return null;
        }
        user.setAskFlag("1");
        user.setAskTime(new Date());
        // 返回更新后的用户对象
        if(userMapper.updateAskFlag(user) > 0) {
            return userMapper.selectByPrimaryKey(user.getUsrId());
        }
        return null;
    }
}
