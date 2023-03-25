package com.example.springbootproject.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String usrId;

    private String usrName;

    private String usrPassword;

    private Integer usrSex = 0;

    private String usrEmail;

    private Date usrRegisterTime;

    private String banFlag;

    private String delFlag;

    private String usrAuthority;

    private String askFlag;

    private  Date askTime;

}