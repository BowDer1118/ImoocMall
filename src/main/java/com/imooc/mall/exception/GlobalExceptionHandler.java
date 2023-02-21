package com.imooc.mall.exception;

import com.imooc.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 用於處理異常的Handler
 */

@ControllerAdvice //攔截Controller拋出的異常
public class GlobalExceptionHandler {
    private final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * 攔截ImoocMallException類異常
     *
     * @param e ImoocMallException類異常
     * @return ApiRestResponse物件
     */
    @ExceptionHandler(ImoocMallException.class)
    @ResponseBody
    public ApiRestResponse handleImoocMallException(ImoocMallException e){
        //紀錄Error資料
        logger.error("ImoocMallException: "+e);
        return ApiRestResponse.error(e.getCode(),e.getMsg());
    }


    /**
     * 攔截Exception類的異常
     * @param e Exception類物件
     * @return ApiRestResponse物件
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e){
        //紀錄Error資料
        logger.error("Default Exception: "+e);
        return ApiRestResponse.error(ImoocMallExceptionEnum.SYSTEM_ERROR);
    }

    /**
     * 處理由@Vaild規定要驗證的內容所拋出的異常
     * 例如:@Size,@NotNull註解所拋出的異常
     * @param e MethodArgumentNotValidException物件
     * @return ApiRestResponse物件
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        logger.error("MethodArgumentNotValidException: "+e);
        ApiRestResponse response=handleBindingResult(e.getBindingResult());
        return response;
    }

    /**
     * 私有的helper function
     * 與handleMethodArgumentNotValidException()一起使用
     * 負責處理MethodArgumentNotValidException中的BindingResult
     * 將錯誤訊息提取出來並放入ApiRestResponse中
     * @param result BindingResult物件
     * @return ApiRestResponse物件
     */
    private ApiRestResponse handleBindingResult(BindingResult result){
        List<String> list=new ArrayList<>();
        //從BindingResult中檢查是否有錯誤相關的訊息
        if (result.hasErrors()) {
            //獲取BindingResult中所有的錯誤訊息
            List<ObjectError> allErrors = result.getAllErrors();
            //將錯誤訊息全部加入list中
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        //檢查錯誤list中是否有資料(通常不可能拋出錯誤，但list中沒有錯誤資訊)
        if(list.size()==0){
            return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARAMETER_ERROR);
        }
        //有錯誤訊息
        String errorMessage=list.toString(); //將錯誤訊息轉成字串
        return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARAMETER_ERROR.getCode(),errorMessage);
    }


}
