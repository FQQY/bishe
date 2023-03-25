package com.example.springbootproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayList {

    private Integer playId;

    private String workId;

    private String usrId;

    private Date comtTime;

    private String delFlag;

}