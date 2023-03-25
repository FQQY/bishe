package com.example.springbootproject.dao;

import com.example.springbootproject.entity.PlayList;
import com.example.springbootproject.entity.PlayListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface PlayListMapper {
    /**
     * 删除稍后播放
     * @param playList
     * @return 删除成功的条数
     */
    int deleteByUsrIdAndVidId(PlayList playList);

    /**
     * 添加稍后播放记录
     * @param playList
     * @return 添加成功的条数
     */
    int insert(PlayList playList);

    /**
     * 查询某一个用户的所有的稍后播放记录
     * @param usrId
     * @return 所有播放记录的详细信息的集合
     */
    ArrayList<PlayListDTO> selectPlayListDTOByUsrId(String usrId);

    /**
     * 判断当前视频是否已经存在于该用户的稍后播放列表
     * @param playList
     * @return 大于0代表已经存在
     */
    int isExistInPlayList(PlayList playList);

    /**
     * 更新加入稍后播放列表的时间
     * @param playList
     * @return 更新成功的条数
     */
    int updateByPrimaryKey(PlayList playList);

    /**
     * 清除或删除用户的收藏列表
     * @param usrId delFlag
     * @return
     */
    int clearResumePlayListByUsrId(@Param("usrId") String usrId, @Param("delFlag") String delFlag);

    /**
     * 根据稍后播放id删除一条记录
     * @param playId
     * @return 删除的记录条数
     */
    int deletePlayListByPlayId(Integer playId);

}