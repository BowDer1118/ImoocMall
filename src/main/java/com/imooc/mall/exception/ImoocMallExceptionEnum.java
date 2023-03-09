package com.imooc.mall.exception;

/**
 * 異常的枚舉類
 * 用來表示Response中的兩個資料
 * 1.status:狀態碼
 * 2.msg:提示訊息
 */
public enum ImoocMallExceptionEnum {
    //定義業務異常的枚舉
    //User Service
    NEED_USER_NAME(10001,"用戶名稱不能為空"),
    NEED_PASSWORD(10002,"密碼不能為空"),
    PASSWORD_TOO_SHORT(10003,"密碼不能小於8位數"),
    NAME_EXIST(10004,"用戶名稱不允許重複，註冊失敗"),
    USER_REGISTER_INSERT_FAILED(10005,"註冊資訊插入資料庫失敗，請重新嘗試"),
    WRONG_PASSWORD(10006,"用戶密碼錯誤"),
    NEED_LOGIN(10007,"用戶未登入"),
    USER_SIGNATURE_FAILED(10008,"用戶個性簽名更新失敗，請重新嘗試"),
    PERMISSION_DENY(10009,"無管理員權限"),
    REQUEST_PARAMETER_ERROR(10010,"輸入的參數有錯誤"),
    //Category Service
    CATEGORY_NAME_EXIST(10011,"分類的名稱已存在，請重新嘗試"),
    CATEGORY_UPDATE_FAILED(10012,"分類更新失敗，請重新嘗試"),
    CATEGORY_DELETE_FAILED(10013,"分類目錄刪除失敗"),
    PRODUCT_NAME_EXIST(10014,"商品名稱已存在"),
    PRODUCT_ADD_FAILED(100015,"商品插入資料庫失敗，請重新嘗試"),
    FILE_UPLOAD_FAILED(10016,"檔案上傳失敗，請重新嘗試"),
    PRODUCT_UPDATE_FAILED(10017,"商品更新失敗，請重新嘗試"),
    PRODUCT_DELETE_FAILED(10018,"商品刪除失敗，請重新嘗試"),
    //定義系統異常的枚舉
    //Global Exception Handler
    SYSTEM_ERROR(20000,"系統異常");
    private Integer code; //異常的號碼
    private String msg; //提示訊息

    ImoocMallExceptionEnum() {
    }

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
