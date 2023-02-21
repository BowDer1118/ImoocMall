package com.imooc.mall.service.impl;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import com.imooc.mall.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

/**
 * UserService實現類
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 提供User物件
     * @param id 用戶id
     * @return User物件
     */
    @Override
    public User getUser(Integer id) {
        User user=userMapper.selectByPrimaryKey(id);
        return user;
    }

    /**
     * 提供用戶註冊功能
     *
     * @param userName 用戶名稱
     * @param password 用戶密碼
     * @throws ImoocMallException 註冊過程發生錯誤
     */
    @Override
    public void register(String userName, String password) throws ImoocMallException, NoSuchAlgorithmException {
        //查詢用戶名稱是否存在，不允許重複命名
        User user=userMapper.selectByName(userName);
        if(user!=null){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXIST);
        }
        //允許註冊
        User newUser=new User();
        newUser.setUsername(userName);
        //對密碼加密
        String md5= MD5Utils.getND5Str(password);
        newUser.setPassword(md5);

        //寫入資料庫
        int count=userMapper.insertSelective(newUser);
        //檢查插入結果(插入新的資料影響應該為1)
        if(count!=1){
            throw new ImoocMallException(ImoocMallExceptionEnum.USER_REGISTER_INSERT_FAILED);
        }
    }

    /**
     * 向資料庫提取用戶訊息，以驗證用戶登入
     * @param userName 用戶名稱
     * @param password 用戶密碼
     * @return 查找成功:User物件，查找失敗:拋出ImoocMallException異常
     * @throws NoSuchAlgorithmException 密碼加密異常
     * @throws ImoocMallException 登陸失敗異常
     */
    @Override
    public User login(String userName, String password) throws NoSuchAlgorithmException, ImoocMallException {
        //先將用戶輸入的密碼轉為加密後的字串
        String md5Password=null;
        md5Password=MD5Utils.getND5Str(password);

        //向資料庫查詢User
        User user=userMapper.selectLogin(userName,md5Password);
        if(user==null){
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    /**
     * 更信用戶簽名
     * @param user User物件
     * @throws ImoocMallException 個性簽名更新失敗
     */
    @Override
    public void updateInformation(User user) throws ImoocMallException {
        int updateCount=userMapper.updateByPrimaryKeySelective(user);
        //檢查更新結果
        if(updateCount!=1){
            throw new ImoocMallException(ImoocMallExceptionEnum.USER_SIGNATURE_FAILED);
        }
    }

    /**
     * 判斷User是否為管理員
     * role=1--->普通用戶，role=2--->管理員
     *
     * @param user 要判斷身分的User物件
     * @return 判斷結果
     */
    @Override
    public boolean checkAdminRole(User user) {
        //1是普通用戶，2是管理員
        return user.getRole().equals(2);
    }
}
