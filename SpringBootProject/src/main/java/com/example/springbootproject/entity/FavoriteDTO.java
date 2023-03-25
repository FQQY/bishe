package com.example.springbootproject.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FavoriteDTO {

    private Integer favId;

    private String workId;

    private String usrId;

    private Integer favbId;

    private String delFlag;

    private Video work;

    // 用于存放批量添加收藏的id数组
    private ArrayList<Integer> favbIds;

}