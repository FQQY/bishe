package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Notice;
import com.example.springbootproject.entity.User;
import com.example.springbootproject.entity.Video;
import com.example.springbootproject.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fqgxing
 * @create 2023-03-11-15:46
 */
@RestController
@RequestMapping("/update")
public class UpdateController {

    @Autowired
    private UpdateService updateService;

    @PostMapping("/notice")
    public int updateNotice(@RequestBody Notice notice) {
        return updateService.updateNotice(notice);
    }

    @PostMapping("/user/info")
    public User updateUserInfo(@RequestBody User user) {
        return updateService.updateUserInfo(user);
    }

    @PostMapping("/work/pass/status")
    public int updateWorkPassStatus(@RequestBody Video video) {
        return updateService.updateWorkPassStatus(video);
    }

    @PostMapping("/agree/userask")
    public boolean agreeUserAsk(@RequestBody User user) {
        return updateService.agreeUserAsk(user.getUsrId());
    }

    @PostMapping("/refuse/userask")
    public boolean refuseUserAsk(@RequestBody User user) {
        return updateService.refuseUserAsk(user);
    }

    @PostMapping("/send/userask")
    public User sendUserAsk(@RequestBody User user) {
        return updateService.sendUserAsk(user);
    }

}
