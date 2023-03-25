package com.example.springbootproject.dao;

import com.example.springbootproject.entity.History;
import com.example.springbootproject.entity.HistoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface HistoryMapper {

    /**
     *  插入数据
     * @param history
     * @return 插入成功的条数
     */
    int insert(History history);

    /**
     * 判断该条记录是否存在
     * @param history
     * @return 查询到的记录条数
     */
    int isExistHistory(History history);

    /**
     * 更新历史记录的观看时间
     * @param history
     * @return 更新成功的条数
     */
    int updateViewTime(History history);

    /**
     *  根据用户id和视频id来更新观看历史表的数据
     * @param history
     * @return 影响的记录数
     */
    int updateByPrimaryKeySelective(History history);

    /**
     *  根据用户id和视频id来删除观看历史表的数据
     * @param history
     * @return 影响的记录数
     */
    int deleteByPrimaryKey(History history);


    /**
     *  根据提供的用户id获取该用户的视频观看记录
     * @param usrId
     * @return 符合条件的视频历史记录
     */
    ArrayList<HistoryDTO> selectHistoryByUsrId(String usrId);

    /**
     *  根据提供的历史id获取该用户的视频观看记录
     * @param hisId
     * @return 符合条件的视频历史记录列表
     */
    List<HistoryDTO> selectVideoHistoryByHisId(String hisId);

    /**
     * 清除或恢复用户的历史记录 用户注销账号后
     * @param usrId
     * @return 返回清除的历史记录条数
     */
    int clearResumeHistoryByUsrId(@Param("usrId") String usrId, @Param("delFlag")String delFlag);

    /**
     * 根据历史记录id删除 用于删除用用户历史中包含已经删除的视频
     * @param hisId
     * @return
     */
    int deleteHistoryByHisId(int hisId);


}