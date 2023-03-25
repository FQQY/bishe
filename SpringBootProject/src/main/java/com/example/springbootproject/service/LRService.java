package com.example.springbootproject.service;

import com.example.springbootproject.dao.RootMapper;
import com.example.springbootproject.dao.UserMapper;
import com.example.springbootproject.entity.Root;
import com.example.springbootproject.entity.User;
import com.example.springbootproject.util.Md5Util;
import com.example.springbootproject.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-02-27-17:37
 */

@Service
public class LRService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RootMapper rootMapper;


    public User register(User user) {
        String UUIDValue = UUIDUtil.getUUID();

        user.setUsrId(UUIDValue);
        // 第二次md5加密
        user.setUsrPassword(Md5Util.getMD5(user.getUsrPassword()));
        user.setUsrRegisterTime(new Date());

        if(userMapper.insertSelective(user) > 0) {
            return userMapper.selectByPrimaryKey(user.getUsrId());
        }

        return null;
    }


    public User userLogin(User user) {
        // 用户密码进行第二次md5加密
        user.setUsrPassword(Md5Util.getMD5(user.getUsrPassword()));

        return userMapper.userLogin(user);
    }

    public Root rootLogin(Root root) {
        // 用户密码进行第二次md5加密
        root.setRootPassword(Md5Util.getMD5(root.getRootPassword()));

        return rootMapper.adminLogin(root);
    }







}
