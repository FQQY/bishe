package com.example.springbootproject.dao;

import com.example.springbootproject.entity.Article;
import com.example.springbootproject.entity.ArticleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author fqgxing
 * @create 2023-03-19-17:33
 */
@Mapper
@Repository
public interface ArticleMapper {

    /**
     * 添加一篇文章到数据库
     * @param article
     * @return 添加成功的文章个数
     */
    int insertArticle(Article article);

    /**
     * 查找所有已经通过审核的文章
     * @param workTitle
     * @return 文章列表
     */
    ArrayList<ArticleDTO> selectArticle(String workTitle);

    /**
     * 查找某一分类下的所有文章
     * @param catId
     * @return 文章列表
     */
    ArrayList<ArticleDTO> selectArticlesByCatId(int catId);

}
