package com.example.springbootproject.controller;

import com.example.springbootproject.entity.*;
import com.example.springbootproject.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fqgxing
 * @create 2023-03-12-13:31
 */
@RestController
@RequestMapping("/delete")
public class DeleteController {

    @Autowired
    private DeleteService deleteService;

    @PostMapping("/notice")
    public boolean delNotice(@RequestBody Notice notice) {

        return deleteService.delNotice(String.valueOf(notice.getNotId()));
    }

    @PostMapping("/playlist")
    public boolean delPlayList(@RequestBody PlayList playList) {
        return deleteService.delPlayList(playList);
    }

    @PostMapping("/favorite")
    public boolean delFavorite(@RequestBody Favorite favorite) {
        return deleteService.delFavorite(favorite);
    }

    @PostMapping("/history")
    public boolean delHistory(@RequestBody History history) {
        return deleteService.delHistory(history);
    }

    @PostMapping("/work")
    public boolean delVideo(@RequestBody Work work) {
        return deleteService.delWork(work);
    }

    @PostMapping("/user")
    public boolean delUser(@RequestBody User user) {
        return deleteService.delUser(user.getUsrId());
    }

    @PostMapping("/favoritebag")
    public boolean delFavoriteBag(@RequestBody FavoriteBag favoriteBag) {
        return deleteService.delFavoriteBag(favoriteBag);
    }

    @PostMapping("/clear/favoritebag")
    public boolean clearFavoriteBag(@RequestBody Favorite favorite) {
        return deleteService.clearFavoriteBag(favorite);
    }


}
