package com.example.springbootproject.dao;


import com.example.springbootproject.entity.Video;
import com.example.springbootproject.entity.VideoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Mapper
@Repository
public interface VideoMapper {

    /**
     * 查询某一个分类下的视频
     * @param catId
     * @return 返回视频信息的集合
     */
    ArrayList<VideoDTO> selectVideosByCatId(Integer catId);

    /**
     * 根据视频标题模糊查询视频 标题可以为空 查询的视频包含类别
     * @param workTitle
     * @return 返回符合条件的视频集合
     */
    ArrayList<VideoDTO> selectAllVideo(String workTitle);

    /**
     * 查询用户上传的所有视频 包括通过和没通过的
     * @param usrId
     * @return 返回视频的集合
     */
    ArrayList<Video> selectVideosByUsrId(String usrId);


    /**
     * 向work作品表插入一条视频记录， 其中workId, usrId, workUploadTime是必传的
     * @param video
     * @return 返回插入成功的条数
     */
    int insertSelective(Video video);


    /**
     * 查询审核通过 已发布的视频
     * @param workId
     * @return
     */
    Video selectPassVideo(String workId);


    //暂时没有用到
    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKeyWithBLOBs(Video record);

    int updateByPrimaryKey(Video record);
}