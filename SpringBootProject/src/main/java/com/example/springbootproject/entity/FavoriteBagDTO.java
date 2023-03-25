package com.example.springbootproject.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FavoriteBagDTO {

    private Integer favbId;

    private String usrId;

    private String favbName;

    private String delFlag;

    private ArrayList<FavoriteDTO> favList;

}