package com.example.springbootproject.controller;

import com.example.springbootproject.dao.HistoryMapper;
import com.example.springbootproject.dao.UserMapper;
import com.example.springbootproject.entity.HistoryDTO;
import com.example.springbootproject.entity.User;
import com.example.springbootproject.util.FileUploadUtil;
import com.example.springbootproject.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author fqgxing
 * @create 2023-02-22-15:17
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HistoryMapper historyMapper;


    @GetMapping(value = "/101", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<HistoryDTO> test(String id) {
        User u = userMapper.selectByPrimaryKey(id);
        List<HistoryDTO> history = historyMapper.selectHistoryByUsrId(id);
        for(HistoryDTO h : history) {
            System.out.println(h.toString());
        }
        System.out.println(history);
        return history;

    }

    @PostMapping("/102")
    @ResponseBody
    public R<String> test01(MultipartFile video) throws IOException {
        String fileCode = FileUploadUtil.uploadVideoByAliyun(video);
        return new R("success",fileCode);
    }

}
