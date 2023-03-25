package com.example.springbootproject.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-03-10-16:48
 */
@Data
public class CommentDTO {

    private Integer comtId;

    private User user;

    private Date comtTime;

    private String comtContent;
}
