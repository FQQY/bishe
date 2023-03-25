package com.example.springbootproject.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Notice {
    private Integer notId;

    private String rootId;

    private String notContent;

    private Integer notLevel;

    private Date notUploadTime;

    private String delFlag;

 }