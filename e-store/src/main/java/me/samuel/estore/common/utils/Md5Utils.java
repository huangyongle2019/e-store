package me.samuel.estore.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class Md5Utils {
    private static final String HASHALGORITHNAME = "MD5";

    private static final int HASHITERATIONS = 1024;

    /**
     * 密码加密
     * @param password
     * @param salt
     * @return
     */
    public static String encrypt(String password, String salt){
        ByteSource source = ByteSource.Util.bytes(salt);
        Object obj = new SimpleHash(HASHALGORITHNAME,password,source,HASHITERATIONS);
        return obj.toString();
    }

    public static boolean matches(String authPassword, String salt, String password){
        String encrytPassword = encrypt(authPassword,salt);
        if (encrytPassword.equals(password)){
            return true;
        }
        return false;
    }

}
