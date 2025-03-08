package com.py.utils;


import cn.hutool.crypto.digest.Digester;

import java.util.UUID;

public class PwdUtil {

    public static String sha256(String str) {
        Digester digester = new Digester("SHA-256");
        return digester.digestHex(str);
    }

    public static String generateSalt() {
        return UUID.randomUUID().toString();
    }

    public static String encrypt(String pwd, String salt) {
        return sha256(pwd + salt);
    }


    public static boolean verify(String verified, String salt, String validate) {
        return encrypt(verified, salt).equals(validate);
    }

    public static boolean verify(String password, String password1) {
        return password.equals(password1);
    }
}
