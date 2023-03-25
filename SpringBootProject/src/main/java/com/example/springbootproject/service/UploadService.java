package com.example.springbootproject.service;

import com.example.springbootproject.dao.VideoMapper;
import com.example.springbootproject.entity.Video;
import com.example.springbootproject.util.FileUploadUtil;
import com.example.springbootproject.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-03-05-20:45
 */
@Service
public class UploadService {

    @Autowired
    private VideoMapper videoMapper;

    private String videoScreenShot = "?x-oss-process=video/snapshot,t_30000,f_jpg,w_0,h_0,m_fast,ar_auto";

    public int uploadWorks(Video video, MultipartFile file) throws IOException {
        video.setWorkId(UUIDUtil.getUUID());
        video.setWorkUploadTime(new Date());
        String url = FileUploadUtil.uploadVideoByAliyun(file);
        video.setWorkSrc(url);
        if(video.getWorkPhoto() == null || video.getWorkPhoto().length() == 0) {
            video.setWorkPhoto(url + videoScreenShot);
        }


        return videoMapper.insertSelective(video);
    }

}
