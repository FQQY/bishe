package com.example.springbootproject.dao;

import com.example.springbootproject.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface CategoryMapper {

    ArrayList<Category> selectAllCategory();

    Category selectByPrimaryKey(Integer catId);

    int insertSelective(String catName);

    int updateByPrimaryKeySelective(Integer catId);

    int deleleByPrimaryKey(Integer catId);
}