package com.example.springbootproject.dao;

import com.example.springbootproject.entity.Root;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RootMapper {

    Root adminLogin(Root root);


    int deleteByPrimaryKey(String rootId);

    int insert(Root record);

    int insertSelective(Root record);

    Root selectByPrimaryKey(String rootId);

    int updateByPrimaryKeySelective(Root record);

    int updateByPrimaryKey(Root record);
}