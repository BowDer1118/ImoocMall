package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

/**
 * 使用者控制器
 */
@Controller
//指定Mapper的位置
@MapperScan(basePackages = "com.imooc.mall.model.dao")
public class UserController {
    @Resource
    private UserService userService;
    /**
     * 提供查詢使用者的功能
     * @return User物件
     */
    @GetMapping("/user")
    @ResponseBody
    public User personalPage(){
        return userService.getUser(1);
    }

    /**
     * 提供用戶註冊的功能
     * @param userName 用戶名稱
     * @param password 用戶密碼
     * @return ApiRestResponse物件
     * @throws ImoocMallException 處理異常
     * @throws NoSuchAlgorithmException 密碼加密異常
     */
    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName, @RequestParam("password") String password) throws ImoocMallException, NoSuchAlgorithmException {
        //驗證用戶名稱
        //使用Spring框架提供的字串比對(同時判斷null和空字串)
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        //驗證密碼
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        //密碼長度不能少於8位
        if(password.length()<8){
            return ApiRestResponse.error(ImoocMallExceptionEnum.PASSWORD_TOO_SHORT);
        }

        //允許用戶註冊
        userService.register(userName,password);

        //註冊過程結束
        return ApiRestResponse.success();
    }

    /**
     * 提供使用戶登入功能
     * 登陸成功:保存User物件到Session中
     * @param userName 用戶名稱
     * @param password 用戶密碼
     * @param session Request中的Session物件
     * @return data欄位為User物件的ApiRestResponse
     * @throws ImoocMallException 登入失敗異常
     * @throws NoSuchAlgorithmException 密碼加解密失敗
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws ImoocMallException, NoSuchAlgorithmException {
        //驗證用戶名稱
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        //驗證用戶密碼
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }

        //驗證登入
        User user=userService.login(userName,password);
        //隱藏User的password之後再包裝成Response避免密碼外洩
        user.setPassword(null);
        //將User物件加入Session中
        session.setAttribute(Constant.IMOOC_MALL_USER,user);
        return ApiRestResponse.success(user);
    }

    /**
     * 更新用戶的個性簽名
     * @param signature 新的個性簽名
     * @param session Session物件，用來比對User物件
     * @return ApiRestResponse物件
     * @throws ImoocMallException 更新失敗異常
     */
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(@RequestParam("signature") String signature,HttpSession session) throws ImoocMallException {
        //先從Session中獲取User物件
        User currentUser= (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        //檢查User物件是否存在
        if(currentUser==null){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        //建立要更新的User物件
        User user=new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);

        //更新User物件
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    /**
     * 登出功能
     * @param session Request中的Session物件
     * @return ApiRestResponse
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session){
        //移除之前加入Session中的User物件
        session.removeAttribute(Constant.IMOOC_MALL_USER);
        return ApiRestResponse.success();
    }

    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws ImoocMallException, NoSuchAlgorithmException {
        //驗證用戶名稱
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        //驗證用戶密碼
        if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }

        //驗證登入
        User user=userService.login(userName,password);
        //檢查當前User的身分是否為管理員
        if (userService.checkAdminRole(user)) {
            //為管理員，執行管裡操作
            //隱藏User的password之後再包裝成Response避免密碼外洩
            user.setPassword(null);
            //將User物件加入Session中
            session.setAttribute(Constant.IMOOC_MALL_USER,user);
            return ApiRestResponse.success(user);
        }else{
            return ApiRestResponse.error(ImoocMallExceptionEnum.PERMISSION_DENY);
        }
    }
}
