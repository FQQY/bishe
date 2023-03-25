package com.example.springbootproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fqiegxing
 * @version 1.0
 * @description: TODO
 * @date 2022/11/25 11:06
 */
@Configuration
public class UploadFilePathConfig implements WebMvcConfigurer {

    // 映射之后访问的路径
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    // 上传的路径
    @Value("${file.uploadFolder}")
    private String uploadFolder;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
    }
}
