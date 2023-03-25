package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Video;
import com.example.springbootproject.service.UploadService;
import com.example.springbootproject.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author fqgxing
 * @create 2023-03-05-20:49
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping(value="/video")
    public R<Integer> uploadVideo(@RequestParam MultipartFile file, Video video) throws IOException {
        if(file.isEmpty())
            return new R("error", null);
        int i = uploadService.uploadWorks(video, file);
        if(i > 0) {

            return new R("success", i);
        } else {
            return new R("error", null);
        }
    }
}
