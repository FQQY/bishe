package com.example.springbootproject.controller;

import com.example.springbootproject.dao.RootMapper;
import com.example.springbootproject.dao.UserMapper;
import com.example.springbootproject.entity.Root;
import com.example.springbootproject.entity.User;
import com.example.springbootproject.service.LRService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fqgxing
 * @create 2023-02-27-17:29
 */
@RestController
public class LRController {

    @Autowired
    private LRService lrService;

    @Autowired
    private  UserMapper userMapper;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return lrService.register(user);
    }


    @GetMapping(value="/confirmEmail")
    public int confirmEmail( String email) {
        return  userMapper.confirmEmail(email);
    }

    /**
     *
     * @param user
     * @return
     */
    @PostMapping( value="/user/login", produces = "application/json;charset=UTF-8")
    public User userLogin(@RequestBody User user) {
        return lrService.userLogin(user);
    }

    @PostMapping( "/admin/login")
    public Root adminLogin(@RequestBody Root root) {
        return lrService.rootLogin(root);
    }


}
