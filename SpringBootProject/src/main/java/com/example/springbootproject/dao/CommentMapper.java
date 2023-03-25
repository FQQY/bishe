package com.example.springbootproject.dao;

import com.example.springbootproject.entity.Comment;
import com.example.springbootproject.entity.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface CommentMapper {

    /**
     * 查询某一个视频的所有评论
     * @param workId
     * @return 包含该视频所有评论的列表
     */
    ArrayList<CommentDTO> selectCommentsByVidId(String workId);

    Comment selectByPrimaryKey(Comment comment);

    int deleteByPrimaryKey(Integer comtId);

    /**
     * 添加评论
     * @param comment
     * @return 添加的评论条数
     */
    int insertComment(Comment comment);

    int updateCommentByUser(Comment comment);

    int updateCommentByAdmin(Integer comtId);

    /**
     * 清空或恢复用户的所有评论
     * @param usrId delFlag
     * @return 清空的评论条数
     */
    int clearResumeCommentByUsrId(@Param("usrId") String usrId, @Param("delFlag") String delFlag);

}