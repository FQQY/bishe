package com.example.springbootproject.service;

import com.example.springbootproject.dao.*;
import com.example.springbootproject.entity.*;
import com.example.springbootproject.util.Md5Util;
import com.example.springbootproject.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * @author fqgxing
 * @create 2023-03-08-19:05
 */
@Service
public class AddService {

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private FavoriteBagMapper favoriteBagMapper;
    @Autowired
    private PlayListMapper playListMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private  NoticeMapper noticeMapper;
    @Autowired
    private  ArticleMapper articleMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private RootMapper rootMapper;

    public int addRoot(Root root) {
        root.setRootPassword(Md5Util.getMD5(root.getRootPassword()));
        root.setRootId(UUIDUtil.getUUID());
        return rootMapper.insertSelective(root);
    }

    public NoticeDTO addNotice(Notice notice) {
        notice.setNotUploadTime(new Date());
        if(noticeMapper.insert(notice) > 0) {
            return noticeMapper.selectByRootIdNoticeContent(notice);
        }
        return null;
    }

    public int addHistory(History history) {
        int result = historyMapper.isExistHistory(history);
        history.setViewTime(new Date());
        if(result > 0) {
            return historyMapper.updateViewTime(history);
        }
        return historyMapper.insert(history);
    }

    public int addComment(Comment comment) {
        comment.setComtTime(new Date());
        return commentMapper.insertComment(comment);
    }

    /**
     * 如果某个收藏夹中已经存在那么就跳过当前文件夹向其他文件夹中添加
     * @param f
     * @return 添加成功的个数
     */
    public int addFavorite(FavoriteDTO f) {

        ArrayList<Integer> existThisFavorite = favoriteMapper.isExistThisFavorite(f);
        if(existThisFavorite.size() == 0){
            return favoriteMapper.insertForVidIdAndUsrIdAndFavbId(f);
        }
        // 说明至少有一个收藏夹中包含此视频
        // 将视频添加到剩余文件夹中
        // 取两个集合的差集
        f.getFavbIds().removeAll(existThisFavorite);


         if(f.getFavbIds().size() == 0) {
            return -1;
         }
        return favoriteMapper.insertForVidIdAndUsrIdAndFavbId(f);
    }

    public int addFavoriteBag(FavoriteBag favoriteBag) {
        FavoriteBag existThisFavoriteBag = favoriteBagMapper.isExistThisFavoriteBag(favoriteBag);
        if(existThisFavoriteBag==null) {
            return favoriteBagMapper.insertForUsrIdAndFavbName(favoriteBag);
        } else {
            return  -1;
        }

    }

    public int addPlayList(PlayList playList) {
        int i = playListMapper.isExistInPlayList(playList);
        playList.setAddTime(new Date());
        if(i > 0) {
            // 已经存在 更新时间
            return playListMapper.updateByPrimaryKey(playList);
        }
        // 添加到稍后播放列表
        return playListMapper.insert(playList);
    }

    /**
     * 发布文章
     * @param article
     * @return 返回boolean值 代表是否发布是否成功
     */
    public boolean addArticle(Article article) {
        article.setWorkUploadTime(new Date());
        article.setWorkId(UUIDUtil.getUUID());
        return articleMapper.insertArticle(article) > 0;
    }

    public Tag addTag(Tag tag) {
        if(tagMapper.insertSelective(tag) > 0) {
            return tagMapper.selectByPrimaryKey(tag.getTagName(), tag.getWorkId());
        }
        return null;
    }
}
