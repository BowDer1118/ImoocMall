package com.imooc.mall.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 存放常量的類
 */
@Component //告訴SpringBoot此類需要被管理(才能使用@Value注入資料)
public class Constant {
    //用於混淆敏感資訊明文的鹽值
    public static final String SALT= "8svbsvjkweDF,.03[";
    public static final String IMOOC_MALL_USER="imooc_mall_user";

    //靜態變數要動態注入要注入在Setter上
    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }
}
