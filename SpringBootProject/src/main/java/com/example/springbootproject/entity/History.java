package com.example.springbootproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {

    private Integer hisId;

    private String workId;

    private String usrId;

    private Date viewTime;

    private String delFlag;

}