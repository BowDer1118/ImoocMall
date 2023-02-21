package com.imooc.mall.util;

import com.imooc.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 */
public class MD5Utils {
    /**
     * 利用MD5對字串進行加密，並返回Base64編碼的字串
     * @param strValue 要加密的字串
     * @return Base64編碼後的加密字串
     * @throws NoSuchAlgorithmException 指定演算法不存在
     */
    public static String getND5Str(String strValue) throws NoSuchAlgorithmException {
        //使用MD5加密演算法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //加鹽混淆
        strValue+= Constant.SALT;
        //對字串進行加密
        byte[] digest = md5.digest(strValue.getBytes());
        //執行Base64編碼
        String result=Base64.encodeBase64String(digest);
        return result;
    }
}
