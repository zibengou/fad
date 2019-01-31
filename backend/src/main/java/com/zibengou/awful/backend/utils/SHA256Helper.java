package com.zibengou.awful.backend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class SHA256Helper {

    static Logger logger = LoggerFactory.getLogger(SHA256Helper.class);

    public static String encode(String inStr){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(inStr.getBytes("UTF-8"));
            return bytesToHexString(messageDigest.digest());
        } catch (Exception e) {
            logger.error("SHA256Helper",e);
        }
        return null;
    }
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
