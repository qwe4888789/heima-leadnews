package com.heima.test;

import com.heima.utils.common.BCrypt;
import org.junit.Test;

public class BCryptTest {

    /**
     * 加密
     */
    @Test
    public void testEncode(){
        //1.随机产生盐
        String salt = BCrypt.gensalt();
        System.out.println(salt);

        //2.使用盐产生密码
        String encodePwd = BCrypt.hashpw("123456", salt);

        System.out.println(encodePwd);
    }

    /**
     * 校验
     */
    @Test
    public void testMatch(){
        String encodePwd = "$2a$10$1zPqGt96X4UPv3u9yGeXWeEYSD8P15L7z9cL3jWtSIVX9GVamnjYO";
        boolean result = BCrypt.checkpw("12345", encodePwd);
        System.out.println(result);
    }
}
