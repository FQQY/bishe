package com.example.springbootproject.dao;

import com.example.springbootproject.entity.Tag;
import com.example.springbootproject.entity.TagDTO;
import org.apache.ibatis.annotations.Mapper;
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

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(String tagId);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);
}