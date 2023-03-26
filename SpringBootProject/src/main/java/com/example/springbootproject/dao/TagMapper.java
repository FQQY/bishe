package com.example.springbootproject.dao;

import com.example.springbootproject.entity.Tag;
import com.example.springbootproject.entity.TagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface TagMapper {

    /**
     * 查询带有指定标签的视频
     * @param tagName
     * @return 标签和视频信息的集合
     */
    ArrayList<TagDTO> selectVideosByTagName(String tagName);
    ArrayList<Tag> selectTagsByVidId(String workId);

    int deleteByPrimaryKey(String tagId);

    int insert(Tag record);

    /**
     * 插入一条记录到标签表
     * @param tag
     * @return 返回插入成功的条数
     */
    int insertSelective(Tag tag);

    /**
     * 查询一条记录 根据标签名字和视频id
     * @param tagName
     * @param workId
     * @return 符合条件的tag对象
     */
    Tag selectByPrimaryKey(@Param("tagName") String tagName, @Param("workId") String workId);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);
}