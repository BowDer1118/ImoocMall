package com.imooc.mall.service;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.model.pojo.User;

import java.security.NoSuchAlgorithmException;

/**
 * 為使用者提供服務
 */
public interface UserService {
    /**
     * 提供User物件
     * @param id 用戶id
     * @return User物件
     */
    public User getUser(Integer id);

    /**
     * 提供用戶註冊功能
     * @param userName 用戶名稱
     * @param password 用戶密碼
     * @throws ImoocMallException 註冊過程發生錯誤
     */
    void register(String userName,String password) throws ImoocMallException, NoSuchAlgorithmException;

    /**
     * 向資料庫提取用戶訊息，以驗證用戶登入
     * @param userName 用戶名稱
     * @param password 用戶密碼
     * @return 查找成功:User物件，查找失敗:拋出ImoocMallException異常
     * @throws NoSuchAlgorithmException 密碼加密異常
     * @throws ImoocMallException 登陸失敗異常
     */
    User login(String userName, String password) throws NoSuchAlgorithmException, ImoocMallException;

    /**
     * 更信用戶簽名
     * @param user User物件
     * @throws ImoocMallException 個性簽名更新失敗
     */
    public void updateInformation(User user) throws ImoocMallException;

    /**
     * 判斷User是否為管理員
     * role=1--->普通用戶，role=2--->管理員
     * @param user 要判斷身分的User物件
     * @return 判斷結果
     */
    public boolean checkAdminRole(User user);
}
