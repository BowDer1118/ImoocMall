package com.imooc.mall.filter;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 管理員驗證過濾器
 */
public class AdminFilter implements Filter {
    @Resource
    private UserService userService;

    /**
     * 用於驗證管理員身分
     * @param servletRequest Request物件
     * @param servletResponse Response物件
     * @param filterChain 過濾器物件
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //獲取當前Session
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session=httpServletRequest.getSession();

        //驗證當前登入的User
        User currentUser= (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        //驗證登入
        if(currentUser==null){
            //獲取PrintWriter用於書寫Response
            PrintWriter writer = ((HttpServletResponse) servletResponse).getWriter();
            //直接書寫Response
            writer.write("{\n" +
                    "    \"statues\": "+ImoocMallExceptionEnum.NEED_LOGIN.getCode()+",\n" +
                    "    \"msg\": "+ImoocMallExceptionEnum.NEED_LOGIN.getMsg()+",\n" +
                    "    \"data\": null\n" +
                    "}");
            writer.flush();
            writer.close();
            return;
        }

        //驗證管理員身分
        if (!userService.checkAdminRole(currentUser)) {
            //獲取PrintWriter用於書寫Response
            PrintWriter writer = ((HttpServletResponse) servletResponse).getWriter();
            //直接書寫Response
            writer.write("{\n" +
                    "    \"statues\": "+ImoocMallExceptionEnum.PERMISSION_DENY.getCode()+",\n" +
                    "    \"msg\": "+ImoocMallExceptionEnum.PERMISSION_DENY.getMsg()+",\n" +
                    "    \"data\": null\n" +
                    "}");
            writer.flush();
            writer.close();
            return;
        }

        //通過所有驗證:放行
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
