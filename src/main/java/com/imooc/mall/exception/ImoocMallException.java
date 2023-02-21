package com.imooc.mall.exception;

/**
 * 自訂的統一異常類
 * 用來給Service層拋出錯誤
 */
public class ImoocMallException extends RuntimeException{
    private final Integer code; //狀態碼
    private final String msg;

    public ImoocMallException(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public ImoocMallException(ImoocMallExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(),exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ImoocMallException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
