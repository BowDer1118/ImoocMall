package com.imooc.mall.util;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MD5UtilsTest {

    @Test
    void getND5Str() throws NoSuchAlgorithmException {
        String str = MD5Utils.getND5Str("12345678");
        System.out.println(str);
    }
}