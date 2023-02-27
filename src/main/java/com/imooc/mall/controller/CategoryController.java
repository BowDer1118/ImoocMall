package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.model.request.AddCategoryRequest;
import com.imooc.mall.model.request.UpdateCategoryRequest;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 目錄Controller
 */
@Controller
public class CategoryController {
    @Resource
    private UserService userService;
    @Resource
    private CategoryService categoryService;

    /**
     * 提供加入分類
     * @param session 用戶發送的Request中的session
     * @param addCategoryRequest 要加入的分類參數，要求驗證其屬性值
     * @return ApiRestResponse物件:處理結果
     */
    @ApiOperation("後台加入分類")
    @PostMapping("/admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session,@Valid @RequestBody AddCategoryRequest addCategoryRequest){
        //驗證當前登入的User
        User currentUser= (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if(currentUser==null){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        //驗證管理員身分
        if (!userService.checkAdminRole(currentUser)) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.PERMISSION_DENY);
        }

        //允許將分類加入系統
        categoryService.add(addCategoryRequest);
        return ApiRestResponse.success();
    }

    /**
     * 後臺更新目錄
     * @param session Request中的Session物件:用來分辨用戶權限
     * @param updateCategoryRequest 要更新的分類資料
     * @return ApiRestResponse
     */
    @ApiOperation("後台更新目錄")
    @PostMapping("/admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(HttpSession session,@RequestBody @Valid UpdateCategoryRequest updateCategoryRequest){
        //驗證當前登入的User
        User currentUser= (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if(currentUser==null){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        //驗證管理員身分
        if (!userService.checkAdminRole(currentUser)) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.PERMISSION_DENY);
        }

        //允許更新分類
        Category category=new Category();
        BeanUtils.copyProperties(updateCategoryRequest,category);
        categoryService.update(category);
        return ApiRestResponse.success();
    }

    /**
     * 刪除分類物件
     * @param id 分類的id
     * @return ApiRestResponse
     */
    @ApiOperation("後台刪除目錄")
    @PostMapping("/admin/category/delete")
    @ResponseBody
    public ApiRestResponse deletedCategory(@RequestParam Integer id){
        categoryService.delete(id);
        return ApiRestResponse.success();
    }

    /**
     * 列出分類的列表
     * @param pageNum 第幾頁
     * @param pageSize 每頁多少個物件
     * @return ApiRestResponse
     */
    @ApiOperation("後台目錄列表")
    @PostMapping("/admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    /**
     * 列出分類的列表
     * @return ApiRestResponse
     */
    @ApiOperation("前台目錄列表")
    @PostMapping("/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustomer(){
        List<CategoryVO> categoryVOS = categoryService.listCategoryForCustomer();
        return ApiRestResponse.success(categoryVOS);
    }
}
