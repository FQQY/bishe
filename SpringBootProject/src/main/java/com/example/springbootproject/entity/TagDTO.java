package com.example.springbootproject.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author fqgxing
 * @create 2023-03-10-19:04
 */
@Data
public class TagDTO {
    private Integer tagId;

    private String workId;

    private String tagName;

    private String delFlag;

    private ArrayList<Video> works;
}
