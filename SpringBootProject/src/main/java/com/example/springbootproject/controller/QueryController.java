package com.example.springbootproject.controller;

import com.example.springbootproject.dao.NoticeMapper;
import com.example.springbootproject.entity.*;
import com.example.springbootproject.service.QueryService;
import com.example.springbootproject.util.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

/**
 * @author fqgxing
 * @create 2023-03-06-13:58
 */
@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private QueryService queryService;
    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping("/category/articles")
    public PageInfo<ArticleDTO> getArticlesByCatId(int catId, int page, int size) {
        return queryService.queryArticlesByCatId(catId, page, size);
    }
    @GetMapping("/page/articles")
    public PageInfo<ArticleDTO> getArticles(String workTitle, int page, int size) {
        return queryService.getArticle(workTitle, page, size);
    }

    @GetMapping("/all/page/works")
    public PageInfo<Video> getAllWorksByPage(int page, int size){
        return queryService.getAllWorksByPage(page, size);
    }

    @GetMapping("/all/works")
    public ArrayList<Video> getAllWorks(){
        return queryService.getAllWorks();
    }

    @GetMapping("/users")
    public ArrayList<User> queryAllUser() {
        return queryService.queryAllUser();
    }

    @GetMapping("/page/users")
    public PageInfo<User> queryAllUserByPage(int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(queryService.queryAllUser());
    }

    @GetMapping("/page/notices")
    public PageInfo<NoticeDTO> queryAllNoticeByPage(int page, int size) {
        return queryService.queryAllNoticeByPage(page, size);
    }

    @GetMapping("/notices")
    public ArrayList<NoticeDTO> queryAllNotice() {
        return noticeMapper.selectALlNotice();
    }

    /**
     * 查询用户上传的视频 接口 分页
     * @param usrId
     * @return
     */
    @GetMapping("/upload/videos")
    public PageInfo<Video> queryUploadVideo(String usrId, int page, int size) {
        return queryService.queryUploadVideos(usrId, page, size);
    }

    @GetMapping("/nopassworks")
    public PageInfo<Video> queryAllNoPassWorks(int page, int size) {
        return queryService.queryAllNoPassWorks(page, size);
    }

    @GetMapping("/work/history")
    public ArrayList<HistoryDTO> queryHistoryByUsrId(String usrId) {
        return queryService.queryHistoryByUsrId(usrId);
    }

    @GetMapping("/page/videos")
    public PageInfo<VideoDTO> getVideos(String workTitle, int page, int size) {
        return queryService.selectAllVideoByPage(workTitle, page, size);
    }

    @GetMapping("/categories")
    public R<List<Category>> queryCategories() {
        List<Category> categories = queryService.selectAllCategory();
        return new R<>("success", categories);
    }

    @GetMapping("/video/tags")
    public ArrayList<Tag> queryVideoTags(String workId) {
        return queryService.selectTagsByVidId(workId);
    }

    @GetMapping("/user/favoritebags")
    public ArrayList<FavoriteBagDTO> queryAllFavoriteBags(String usrId) {
        return queryService.queryFavoriteBagsWithUsrId(usrId);
    }

    @GetMapping("/work/comments")
    public ArrayList<CommentDTO> queryVideoAllComments(String workId) {
        return queryService.queryVideoAllComments(workId);
    }

    @GetMapping("/category/videos")
    public PageInfo<VideoDTO> queryVideosByCatId(Integer catId, int page, int size) {
        return queryService.queryVideosByCatId(catId,page,size);
    }

    @GetMapping("/user")
    public User getUser(String usrId) {
        return queryService.getUser(usrId);
    }

    @GetMapping("/category")
    public Category getCategory(Integer catId) {
        return queryService.getCategory(catId);
    }

    @GetMapping("/work/playlist")
    public ArrayList<PlayListDTO> queryPlayListDTOByUsrId(String usrId) {
        return queryService.queryPlayListDTOByUsrId(usrId);
    }

    @GetMapping("/user/ask")
    public PageInfo<User> getUerAsk(int page, int size) {
        return queryService.getUserAsk(page, size);
    }
}
