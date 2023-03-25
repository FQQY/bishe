package com.example.springbootproject.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-03-16-17:00
 */
@Data
public class NoticeDTO {
    private Integer notId;

    private String rootId;

    private String notContent;

    private Integer notLevel;

    private Date notUploadTime;

    private String delFlag;

    private Root root;
}
