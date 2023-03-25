package com.example.springbootproject.dao;

import com.example.springbootproject.entity.Notice;
import com.example.springbootproject.entity.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface NoticeMapper {

    Notice selectByNotId(String notId);

    /**
     * 删除公告
     * @param notId
     * @return 返回删除成功的公告条数
     */
    int deleteByNotId(String notId);

    /**
     * 添加系统公告
     * @param notice
     * @return 返回添加成功的公告条数
     */
    int insert(Notice notice);

    /**
     * 根据root_id 和 公告内容查询公告
     * @param notice
     * @return 返回符合查询条件的公告
     */
    NoticeDTO selectByRootIdNoticeContent(Notice notice);

    /**
     * 查询所有公告
     * @return 返回符合查询条件的公告集合
     */
    ArrayList<NoticeDTO> selectALlNotice();


    int insertSelective(Notice notice);

    /**
     * 更新公告的等级
     * @param notice
     * @return 返回更新成功的条数
     */
    int updateByNotId(Notice notice);
}