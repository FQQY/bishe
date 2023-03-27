package com.example.springbootproject.dao;

import com.example.springbootproject.entity.Root;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RootMapper {

    Root adminLogin(Root root);

    /**
     * 查看当前管理员账号是否已经注册
     * @param rootAccountNumber
     * @return 查找到符合条件的个数
     */
    int isExistThisRoot(String rootAccountNumber);

    int deleteByPrimaryKey(String rootId);

    int insert(Root record);

    /**
     * 添加一名管理员
     * @param record
     * @return 添加成功的个数
     */
    int insertSelective(Root record);

    Root selectByPrimaryKey(String rootId);

    int updateByPrimaryKeySelective(Root record);

    int updateByPrimaryKey(Root record);
}