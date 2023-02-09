package com.michal.car_rental_app.user.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class MD5Encoder {

    private static final String ALGORITHM = "MD5";
    private static final int HEX_BASE = 16;

    public String getMD5Hash(String password) {
        String md5Hash = null;
        if (password != null) {
            try {
                md5Hash = md5Hash(password, md5Hash);
            } catch (Exception e) {
                log.error("MD5Encoder error: " + e.getMessage(), e);
            }
        }
        return md5Hash;
    }

    private String md5Hash(String password, String md5Hash) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
        digest.update(password.getBytes(), 0, password.length());
        md5Hash = new BigInteger(1, digest.digest()).toString(HEX_BASE);
        return md5Hash;
    }
}
