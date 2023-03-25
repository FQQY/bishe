package com.example.springbootproject.service;

import com.example.springbootproject.dao.*;
import com.example.springbootproject.entity.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author fqgxing
 * @create 2023-03-06-14:25
 */

@Service
public class QueryService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private FavoriteBagMapper favoriteBagMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PlayListMapper playListMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private WorkMapper workMapper;

    public PageInfo<ArticleDTO> getArticle(String workTitle, int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(articleMapper.selectArticle(workTitle));
    }
    public PageInfo<User> getUserAsk( int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(userMapper.selectUserAsk());
    }

    public PageInfo<Video> getAllWorksByPage(int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(workMapper.getAllWorks());
    }
    public ArrayList<Video> getAllWorks() {
        return workMapper.getAllWorks();
    }

    public ArrayList<User> queryAllUser(){
        return userMapper.selectAllUser();
    }

    public PageInfo<NoticeDTO> queryAllNoticeByPage(int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(noticeMapper.selectALlNotice());
    }

    public PageInfo<Video> queryAllNoPassWorks(int page, int size) {
        PageHelper.startPage(page, size);

        return new PageInfo<>(workMapper.selectNoPassWorks());
    }
    public PageInfo<Video> queryUploadVideos(String usrId, int page, int size) {
        PageHelper.startPage(page, size);
        ArrayList<Video> videos = videoMapper.selectVideosByUsrId(usrId);
        return new PageInfo<>(videos);
    }

    public ArrayList<PlayListDTO> queryPlayListDTOByUsrId(String usrId) {
        ArrayList<PlayListDTO> playListDTOS = playListMapper.selectPlayListDTOByUsrId(usrId);

        //
        Iterator<PlayListDTO> playListDTOIterator = playListDTOS.iterator();

        while(playListDTOIterator.hasNext()) {
            PlayListDTO playListDTO = playListDTOIterator.next();
            VideoDTO videoDTO = playListDTO.getWorkDTO();
            if("1".equals(videoDTO.getDelFlag()) || "2".equals(videoDTO.getPassFlag())) {
                // 稍后播放列表含有已经删除的作品
                // 在数据库playList表中删除此条记录
                playListMapper.deletePlayListByPlayId(playListDTO.getPlayId());
                // 从返回的稍后播放列表中删除
                playListDTOIterator.remove();
            } else {
                //
                // 查询视频的类别和上传用户等信息
                if(videoDTO.getCategory() == null) {
                    videoDTO.setCategory(categoryMapper.selectByPrimaryKey(videoDTO.getCatId()));
                }
                if(videoDTO.getUser() == null) {
                    User u = userMapper.selectByPrimaryKey(videoDTO.getUsrId());
                    videoDTO.setUser(u);
                }
            }
        }





        return playListDTOS;
    }

    public Category getCategory(Integer catId) {
        return categoryMapper.selectByPrimaryKey(catId);
    }
    public User getUser(String usrId) {
        return userMapper.selectByPrimaryKey(usrId);
    }
    public ArrayList<HistoryDTO> queryHistoryByUsrId(String usrId) {
        // 查询历史记录
        ArrayList<HistoryDTO> historyDTOS = historyMapper.selectHistoryByUsrId(usrId);
        // 获取迭代器
        Iterator<HistoryDTO> iterator = historyDTOS.iterator();
        // iterator的next()方法在循环中只能调用一次
        while(iterator.hasNext()) {
            HistoryDTO historyDTO = iterator.next();
            VideoDTO videoDTO = historyDTO.getWorkDTO();
            // 该条视频也被删除
            if("1".equals(videoDTO.getDelFlag())|| "2".equals(videoDTO.getPassFlag())) {
                // 删除数据库中的此条记录 iterator.next().getHisId()
                historyMapper.deleteHistoryByHisId(historyDTO.getHisId());
                // 从返回的视频记录列表中删除
                iterator.remove();
            } else {
                // 此条记录存在继续查询视频的上传者信息和视频的分类信息
                // 查询视频的类别和上传用户等信息
                videoDTO.setCategory(categoryMapper.selectByPrimaryKey(videoDTO.getCatId()));
                videoDTO.setUser(userMapper.selectByPrimaryKey(videoDTO.getUsrId()));
            }
        }

        return historyDTOS;
    }

    public ArrayList<CommentDTO> queryVideoAllComments(String workId) {
        return commentMapper.selectCommentsByVidId(workId);
    }

    public ArrayList<FavoriteBagDTO> queryFavoriteBagsWithUsrId(String usrId) {
        ArrayList<FavoriteBagDTO> favoriteBagDTOS = favoriteBagMapper.selectByUsrId(usrId);

        Iterator<FavoriteBagDTO> favoriteBagDTOIterator = favoriteBagDTOS.iterator();
        while(favoriteBagDTOIterator.hasNext()) {
            FavoriteBagDTO favoriteBagDTO = favoriteBagDTOIterator.next();
            Iterator<FavoriteDTO> favoriteDTOIterator = favoriteBagDTO.getFavList().iterator();
            while (favoriteDTOIterator.hasNext()) {
                FavoriteDTO favoriteDTO = favoriteDTOIterator.next();
                if("1".equals(favoriteDTO.getWork().getDelFlag()) || "2".equals(favoriteDTO.getWork().getPassFlag())) {
                    System.out.println(favoriteDTO.getFavId());
                    // 该收藏视频已经被删除
                    // 更改数据库数据 favorite表 delFalg置1
                    favoriteMapper.deleteFavoriteByFavId(favoriteDTO.getFavId());
                    // 从返回列表中删除
                    favoriteDTOIterator.remove();
                }
            }
        }

        return favoriteBagDTOS;
    }
    public PageInfo<VideoDTO> selectAllVideoByPage(String vidTitle, int page, int size) {
        PageHelper.startPage(page, size);
        ArrayList<VideoDTO> videos = videoMapper.selectAllVideo(vidTitle);
        PageInfo<VideoDTO> videoPageInfo = new PageInfo<>(videos);
        return videoPageInfo;
    }

    public ArrayList<Category> selectAllCategory() {
        return categoryMapper.selectAllCategory();
    }

    public ArrayList<Tag> selectTagsByVidId(String vidId) {
        return tagMapper.selectTagsByVidId(vidId);
    }

    public PageInfo<VideoDTO> queryVideosByCatId(Integer catId, int page, int size) {
        PageHelper.startPage(page, size);
        ArrayList<VideoDTO> videos = videoMapper.selectVideosByCatId(catId);
        PageInfo<VideoDTO> videoPageInfo = new PageInfo<>(videos);
        return videoPageInfo;
    }

    public PageInfo<ArticleDTO> queryArticlesByCatId(Integer catId, int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(articleMapper.selectArticlesByCatId(catId));
    }

}
