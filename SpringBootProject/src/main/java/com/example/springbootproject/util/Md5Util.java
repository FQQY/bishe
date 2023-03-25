package com.example.springbootproject.util;


import org.springframework.util.DigestUtils;


/**
 * @author fqgxing
 * @create 2023-02-27-20:30
 */
public class Md5Util {

    public static String getMD5(String str) {

        str += "fqiegxing";
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

}
