package com.imooc.mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 攔截Request
 * 並記錄Request和Response資訊
 */
@Aspect //定義Class為切面類
@Component
public class WebLogAspect {
    //建立Logger物件:用來記錄Log
    private final Logger log=LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 定義攔截點
     * 攔截所有Controller類的function
     */
    @Pointcut("execution(public * com.imooc.mall.controller.*.*(..))")
    public void webLog(){

    }

    /**
     * [前置處理]
     * 在Request到達Server時，進行處理
     * 保存各種Request訊息到Log檔案中
     * 1.URL
     * 2.HTTP_METHOD
     * 3.IP
     * 4.CLASS_METHOD
     * 5.ARGS
     * @param joinPoint
     */
    @Before("webLog()") //前置處理
    public void doBefore(JoinPoint joinPoint){
        //獲取Request
        //1.ServletRequestAttributes
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //2.從Attributes裡面拿到Request物件
        HttpServletRequest request = requestAttributes.getRequest();


        //記錄Request內容
        //URL
        log.info("URL : "+request.getRequestURL().toString());
        //Request的method
        log.info("HTTP_METHOD : "+request.getMethod());
        //IP資訊
        log.info("IP : "+request.getRemoteAddr());
        //執行時期所執行的類與該方法
        String classAndMethodInfo=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        log.info("CLASS_METHOD : "+classAndMethodInfo);
        //執行時期所傳入的參數
        Object[] args = joinPoint.getArgs();
        log.info("ARGS : "+ Arrays.toString(args));
    }

    /**
     * [後置處理]
     * 處理完Request之後，Server端所返回的內容
     * 可能為JSON或某個物件
     * 紀錄Server回傳的Response內容
     * @param res Server端產生的Response
     */
    @AfterReturning(returning = "res",pointcut = "webLog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        //紀錄Server回傳的Response內容
        //使用ObjectMapper().writeValuesAsString將JSON物件轉成String
        String responseString = new ObjectMapper().writeValueAsString(res);
        log.info("RESPONSE : "+responseString);
    }
}
