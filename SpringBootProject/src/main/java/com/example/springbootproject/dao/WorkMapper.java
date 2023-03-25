package com.example.springbootproject.dao;

import com.example.springbootproject.entity.Video;
import com.example.springbootproject.entity.Work;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author fqgxing
 * @create 2023-03-23-7:44
 */
@Mapper
@Repository
public interface WorkMapper {

    /**
     * 查找所有未审核的视频
     * @return
     */
    ArrayList<Video> selectNoPassWorks();

    /**
     * 获取所有视频 包括删除的
     * @return 返回的视频集合
     */
    ArrayList<Video> getAllWorks();

    /**
     * 更新视频的状态 0 待审核 1 审核通过 2 审核失败
     * @param video
     * @return
     */
    int updateWorkPassStatus(Video video);

    /**
     * 用于清除或恢复用户发布的作品 用户注销后的操作 用户被封禁 和解封
     * @param usrId
     * @return 删除的作品个数
     */
    int clearResumeWorksByUsrId(@Param("usrId") String usrId, @Param("delFlag") String delFlag);

    /**
     * 取消上传或者删除作品
     * @param work
     * @return 返回删除成功的调试
     */
    int deleteWork(Work work);

}
