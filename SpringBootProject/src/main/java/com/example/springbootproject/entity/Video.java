package com.example.springbootproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    private String workId;

    private String usrId;

    private Integer catId;

    private String workSrc;

    private String workDuration;

    private Date workUploadTime;

    private String passFlag;

    private String delFlag;

    private String workContent;

    private String workTitle;

    private  String workPhoto;


}