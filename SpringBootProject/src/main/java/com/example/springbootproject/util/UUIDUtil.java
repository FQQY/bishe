package com.example.springbootproject.util;

import java.util.UUID;

/**
 * @author fqgxing
 * @create 2023-02-27-20:26
 */
public class UUIDUtil {

    /**
     * 随机生成UUID
     * @return 生成的UUID 保留八位
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,8);
    }


}
