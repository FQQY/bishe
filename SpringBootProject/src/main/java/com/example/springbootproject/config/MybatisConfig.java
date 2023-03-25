package com.example.springbootproject.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
public class MybatisConfig {

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();

        Properties p = new Properties();
        p.setProperty("helperDialect","mysql"); //设置方言，设置数据库
        //offsetAsPageNum，当该参数设置为true时，可以用页码和页面大小两个参数进行分页
        p.setProperty("offsetAsPageNum","true");
        //rowBoundWithCount,当该参数设置为true时，会进行count查询
        p.setProperty("rowBoundWithCount","true");
        //分页合理化开启
        //启用合理化时，pageNum<=0 时会查询第一页，
        // pageNum>pages（超过总数时），会查询最后一页。
        // 默认false 时，如果pageNum<=0或者pageNum>pages（超过总数时）会返回空数据。
        p.setProperty("reasonable","true");

        pageHelper.setProperties(p);

        return pageHelper;
    }

}
