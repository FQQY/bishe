package com.example.springbootproject.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-03-06-14:44
 */
@Data
public class VideoDTO {
    private String workId;

    private String usrId;

    private Integer catId;

    private User user;

    private Category category;

    private String workSrc;

    private String workDuration;

    private Date workUploadTime;

    private String passFlag;

    private String delFlag;

    private String workContent;

    private String workTitle;

    private  String workPhoto;

}
