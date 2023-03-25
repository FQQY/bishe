package com.example.springbootproject.service;

import com.example.springbootproject.dao.*;
import com.example.springbootproject.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fqgxing
 * @create 2023-03-12-13:30
 */
@Service
public class DeleteService {

    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private PlayListMapper playListMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FavoriteBagMapper favoriteBagMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private WorkMapper workMapper;

    // 注销用户
    public boolean delUser(String usrId) {
        if(userMapper.deleteUserByUsrId(usrId) > 0) {
            // 删除该用户的所有作品
            workMapper.clearResumeWorksByUsrId(usrId,"1");
            // 删除该用户的历史记录
            historyMapper.clearResumeHistoryByUsrId(usrId, "1");
            // 删除该用户的收藏和收藏夹
            favoriteMapper.clearResumeFavoriteByUsrId(usrId,"1");
            favoriteBagMapper.clearResumeFavoriteBagByUsrId(usrId,"1");
            // 删除该用户的稍后播放
            playListMapper.clearResumePlayListByUsrId(usrId,"1");
            // 删除用户的评论
            commentMapper.clearResumeCommentByUsrId(usrId,"1");

            return true;
        }

        return false;
    }
    public boolean delNotice(String notId) {
        return noticeMapper.deleteByNotId(notId) > 0;
    }
    public boolean delWork(Work work) {
        return workMapper.deleteWork(work) > 0;
    }
    public boolean delPlayList(PlayList playList){
        return playListMapper.deleteByUsrIdAndVidId(playList) > 0;
    }

    public boolean delFavorite(Favorite favorite) {
        return favoriteMapper.deleteByUsrIdAndFavbId(favorite) > 0;
    }

    public boolean delHistory(History history) {
        return historyMapper.deleteByPrimaryKey(history) > 0;
    }

    public boolean delFavoriteBag(FavoriteBag favoriteBag) {
        // 删除收藏夹的同时删除收藏夹里的所有作品
        return favoriteBagMapper.deleteByFavbIdAndUsrId(favoriteBag)>0 &&
                favoriteMapper.delFavoriteByUsrIdFavbId(favoriteBag.getUsrId(), favoriteBag.getFavbId()) > 0;
    }

    // 清空收藏夹里的所有内容
    public boolean clearFavoriteBag(Favorite favorite) {
        return favoriteMapper.delFavoriteByUsrIdFavbId(favorite.getUsrId(), favorite.getFavbId()) > 0;
    }




}
