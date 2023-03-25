package com.example.springbootproject.dao;

import com.example.springbootproject.entity.FavoriteBag;
import com.example.springbootproject.entity.FavoriteBagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface FavoriteBagMapper {

    /**
     * 查询某个用户创建的所有收藏夹
     * @param usrId
     * @return 所有收藏夹的详细信息
     */
    ArrayList<FavoriteBagDTO> selectByUsrId(String usrId);

    /**
     * 根据收藏夹id查询收藏夹
     * @param favbId
     * @return 符合条件的收藏夹的信息
     */
    FavoriteBag selectByFavbId(Integer favbId);

    /**
     * 删除收藏夹
     * @param favoriteBag(收藏夹id 和 创建用户id)
     * @return 删除成功的条数
     */
    int deleteByFavbIdAndUsrId(FavoriteBag favoriteBag);

    /**
     * 新建收藏夹
     * @param favoriteBag
     * @return 成功创建的收藏夹个数
     */
    int insertForUsrIdAndFavbName(FavoriteBag favoriteBag);

    /**
     * 更新收藏夹信息
     * @param favoriteBag(收藏夹id 和 创建用户id)
     * @return 更新成功的条数
     */
    int updateByFavbIdAndUsrId(FavoriteBag favoriteBag);

    /**
     * 清空或恢复用户的收藏夹
     * @param usrId delFlag
     * @return 清空的收藏夹条数
     */
    int clearResumeFavoriteBagByUsrId(@Param("usrId") String usrId, @Param("delFlag") String delFlag);

    /**
     * 判断用户新建的收藏夹是否存在
     * @param favoriteBag
     * @return 返回存在的收藏夹信息 否则返回null
     */
    FavoriteBag isExistThisFavoriteBag(FavoriteBag favoriteBag);
}