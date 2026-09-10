package com.heima.test;

import com.heima.utils.common.JwtUtils;
import com.heima.utils.common.Payload;
import com.heima.utils.common.RsaUtils;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {
    public String privateKeyPath = "D:\\idea_codes\\javaee153\\rsa\\rsa-key";
    public String publicKeyPath = "D:\\idea_codes\\javaee153\\rsa\\rsa-key.pub";
    /**
     * 生成token
     *
     */
    @Test
    public void testGenerateToken() throws Exception {
        /**
         * 参数一：存入token载荷数据（登录用户数据）
         * 参数二：私钥内容
         * 参数三：过期时间（分钟）
         */
        Integer userId = 1001;

        //读取私钥文件
        PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);

        String token = JwtUtils.generateTokenExpireInMinutes(userId, privateKey, 1);

        System.out.println(token);
    }

    /**
     * 校验token
     */
    @Test
    public void testValidToken() throws Exception {
        /**
         * 参数一：需要校验的token字符串
         * 参数二：传入解密的公钥内容
         * 参数三：载荷内容的类型
         */
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyIjoiMTAwMSIsImp0aSI6Ik0yUm1aV0l4TlRrdFpqSTFZeTAwWXpjeExUaGtaVE10TURsaE0yVTJNVE5oTlRVeCIsImV4cCI6MTYyNjg2MTAxMn0.HXP3khUfWkUJtIjJFK2NFfvhvRiDSPbWaLfw7wDOfO46I7YAzExUVB87Be1ZI38JYWVu7hhENfOeTxwNT6Yw2etKKmtUZsu6Ra3CFJGn_LV0zbnopy-smXvy6Qoh9UQzQCnk0ppBjJ0gulbuYMnRshhT19igky0TXCU7_gt3vNKqmr6wut43ni4gSKPykhiDZS8AyZD3cKqK-n1Mhc00FVpkEJfIM_2ldRn8KfKKE1uve4tZJiE4M6YEz83OB85w2AUjMm4SC2KoEvlKt-F5U00eTCtougCwO7ntuSf7GH1meFDJ2KpVrQkmomxyyeBgF73MdFSxlEmXzDLUhbSFvw";

        PublicKey publicKey = RsaUtils.getPublicKey(publicKeyPath);

        /**
         * Payload:载荷对象，包含id，exp，用户信息
         */
        Payload<Integer> payload = null;

        try {
            payload = JwtUtils.getInfoFromToken(token, publicKey, Integer.class);
            Integer userId = payload.getInfo();
            System.out.println("校验成功，userId:"+userId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("校验失败");//token非法，公钥非法，token过期
        }

    }
}
