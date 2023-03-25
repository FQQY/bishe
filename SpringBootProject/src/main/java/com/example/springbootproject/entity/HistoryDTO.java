package com.example.springbootproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fqgxing
 * @create 2023-02-23-17:10
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {

    private Integer hisId;

    private String workId;

    private String usrId;

    private Date viewTime;

    private String delFlag;

    private VideoDTO workDTO;

}
