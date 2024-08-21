package com.graywolfz.bms.utils;
/*
  生成Token的工具类：
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import java.util.Base64;

/**
 * 生成Token的工具类
 */
public class TokenProcessor {

    private static final Logger log = LoggerFactory.getLogger(TokenProcessor.class);

    private TokenProcessor() {
    }

    private static final TokenProcessor instance = new TokenProcessor();

    public static TokenProcessor getInstance() {
        return instance;
    }

    /**
     * 生成Token
     *
     * @return String
     */
    public String makeToken() {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(token.getBytes());
            return Base64.getEncoder().encodeToString(md5);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            log.error("生成Token异常 Error message: ", e);
        }
        return null;
    }
}
