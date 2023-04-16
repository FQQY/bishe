package com.example.springbootproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-03-12-16:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayListDTO {

    private Integer playId;

    private String workId;

    private String usrId;

    private Date addTime;

    private String delFlag;

    private VideoDTO workDTO;
}
