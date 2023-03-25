package com.example.springbootproject.dao;

import com.example.springbootproject.entity.Favorite;
import com.example.springbootproject.entity.FavoriteDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface FavoriteMapper {

    ArrayList<FavoriteDTO> queryByFavbId(Integer favbId);

    Favorite queryByUsrIdAndFavbId(Favorite favorite);

    int deleteByUsrIdAndFavbId(Favorite favorite);

    /**
     * 添加一条收藏记录
     * @param favoriteDTO
     * @return
     */
    int insertForVidIdAndUsrIdAndFavbId(FavoriteDTO favoriteDTO);

    int updateByVidIdAndUsrId(Favorite favorite);

    /**
     * 清除或恢复用户的收藏
     * @param usrId
     * @return 返回删除的收藏条数
     */
    int clearResumeFavoriteByUsrId(@Param("usrId") String usrId, @Param("delFlag") String delFlag);

    /**
     * 根据收藏id删除收藏表的数据
     * @param favId
     * @return 删除成功的条数
     */
    int deleteFavoriteByFavId(Integer favId);

    /**
     * 判断该用户在选择的几个收藏夹中是否已经收藏此视频
     * @param favoriteDTO
     * @return 如果已经收藏返回此视频所在的收藏夹id 否则返回空？
     */
    ArrayList<Integer> isExistThisFavorite(FavoriteDTO favoriteDTO);

    /**
     * 删除某个用户的某个收藏夹里的所有作品
     * @param usrId
     * @param favbId
     * @return
     */
    int delFavoriteByUsrIdFavbId(@Param("usrId") String usrId, @Param("favbId") Integer favbId);
}