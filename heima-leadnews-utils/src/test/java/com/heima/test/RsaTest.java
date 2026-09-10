package com.heima.test;

import com.heima.utils.common.RsaUtils;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RsaTest {

    public String publicKeyPath = "D:\\idea_codes\\javaee153\\rsa\\rsa-key.pub";
    public String privateKeyPath = "D:\\idea_codes\\javaee153\\rsa\\rsa-key";

    /**
     * 产生公私钥
     */
    @Test
    public void testGenerateKey() throws Exception {
        /**
         * 参数一：公钥存放路径
         * 参数二：私钥存放路径
         * 参数三：加密因子
         * 参数四：文件大小  2048
         */
        RsaUtils.generateKey(publicKeyPath,privateKeyPath,"itheima",2048);
    }

    /**
     * 读取公钥
     */
    @Test
    public void testGetPublicKey() throws Exception {
        PublicKey publicKey = RsaUtils.getPublicKey(publicKeyPath);
        System.out.println(publicKey);
    }

    /**
     * 读取私钥
     */
    @Test
    public void testGetPrivateKey() throws Exception {
        PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);
        System.out.println(privateKey);
    }
}
