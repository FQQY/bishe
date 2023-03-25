package com.example.springbootproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-03-21-12:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    private String workId;

    private String usrId;

    private Integer catId;

    private Date workUploadTime;

    private String passFlag;

    private String delFlag;

    private String workContent;

    private String workTitle;

    private User user;

    private Category category;
}
