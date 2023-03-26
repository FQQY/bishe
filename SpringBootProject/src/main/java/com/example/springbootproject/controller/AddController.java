package com.example.springbootproject.controller;

import com.example.springbootproject.entity.*;
import com.example.springbootproject.service.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fqgxing
 * @create 2023-03-08-19:01
 */
@RestController
@RequestMapping("/add")
public class AddController {

    @Autowired
    private AddService addService;

    @PostMapping("/favorite")
    public int addFavorite(@RequestBody FavoriteDTO f) {
        return addService.addFavorite(f);
    }

    @PostMapping("/favoritebag")
    public int addFavoriteBag(@RequestBody FavoriteBag favoriteBag) {
        return addService.addFavoriteBag(favoriteBag);
    }

    @PostMapping("/playlist")
    public int addPlayList(@RequestBody PlayList playList) {
        return addService.addPlayList(playList);
    }

    @PostMapping("/comment")
    public int addComment(@RequestBody Comment comment) {
        return addService.addComment(comment);
    }

    @PostMapping("/history")
    public int addHistory(@RequestBody History history) {
        return addService.addHistory(history);
    }

    @PostMapping("/notice")
    public NoticeDTO addNotice(@RequestBody Notice notice) {
        return addService.addNotice(notice);
    }

    @PostMapping("/article")
    public boolean addArticle(@RequestBody Article article) {
        return addService.addArticle(article);
    }

    @PostMapping("/tag")
    public Tag addTag(@RequestBody Tag tag) {
        return addService.addTag(tag);
    }

}
