package com.example.springbootproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-03-23-7:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work {

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
