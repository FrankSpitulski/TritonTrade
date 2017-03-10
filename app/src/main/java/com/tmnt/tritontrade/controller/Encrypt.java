package com.tmnt.tritontrade.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encrypt {
    static String gensalt(){
        return new BigInteger(130, new SecureRandom()).toString(32);
    }


    public static String hashpw(String password, String salt) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update((password + salt).getBytes());
        }catch (NoSuchAlgorithmException e)
        {
            return null;
        }
        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
