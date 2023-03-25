package com.example.springbootproject.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-03-19-17:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private String workId;

    private String usrId;

    private Integer catId;

    private Date workUploadTime;

    private String passFlag;

    private String delFlag;

    private String workContent;

    private String workTitle;

}
