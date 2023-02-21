package com.imooc.mall.common;

import com.imooc.mall.exception.ImoocMallExceptionEnum;

/**
 * [泛型類]
 * 通用的Response物件，代表Server處理Request的結果
 * 對照到接口文檔中的Response內容
 * 1.status:狀態碼
 * 2.msg:提示訊息
 * 3.data:泛型的物件，代表回傳的資料
 */
public class ApiRestResponse<T> {
    private Integer statues; //狀態碼
    private String msg; //提示訊息
    private T data; //泛型的物件，代表回傳的資料

    //定義正常Response的狀態碼和提示訊息
    private static final int OK_CODE = 10000;
    private static final String OK_MSG="SUCCESS";

    //Constructors
    //默認的Constructor代表Response成功
    public ApiRestResponse() {
        //使用兩個參數的構造函數進行構造
        this(OK_CODE,OK_MSG);
    }

    public ApiRestResponse(Integer statues, String msg) {
        this.statues = statues;
        this.msg = msg;
    }

    public ApiRestResponse(Integer statues, String msg, T data) {
        this.statues = statues;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 當Response Success時調用
     * @return ApiRestResponse物件
     * @param <T> 泛型資料
     */
    public static <T> ApiRestResponse<T> success(){
        //因為Response Success所以調用默認的Constructor
        return new ApiRestResponse<>();
    }

    /**
     * 當Response Success且需要返回data物件時調用
     * @param result Response要回傳的data物件
     * @return ApiRestResponse物件
     * @param <T> data的資料類型
     */
    public  static <T> ApiRestResponse<T> success(T result){
        ApiRestResponse<T> response=new ApiRestResponse<>();
        response.setData(result);
        return response;
    }

    /**
     * 當Response失敗時調用
     * @param ex 一個異常的枚舉類物件，用來提示Response失敗
     * @return ApiRestResponse物件
     * @param <T> ApiRestResponse的泛型
     */
    public static <T> ApiRestResponse<T> error(ImoocMallExceptionEnum ex){
        return new ApiRestResponse<>(ex.getCode(), ex.getMsg());
    }

    /**
     * 當Response失敗時調用
     * @param code 異常代號
     * @param msg 提示訊息
     * @return ApiRestResponse物件
     * @param <T> ApiRestResponse的泛型
     */
    public static <T> ApiRestResponse<T> error(Integer code,String msg){
        return new ApiRestResponse<>(code,msg);
    }

    //Getters & Setters
    public Integer getStatues() {
        return statues;
    }

    public void setStatues(Integer statues) {
        this.statues = statues;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "statues=" + statues +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
