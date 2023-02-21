package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryRequest;
import com.imooc.mall.model.request.UpdateCategoryRequest;
import com.imooc.mall.model.vo.CategoryVO;

import java.util.List;

/**
 * 分類目錄Service
 */
public interface CategoryService {
    /**
     * 提供目錄新增的服務
     * @param addCategoryRequest 要新增的目錄資訊
     */
    public void add(AddCategoryRequest addCategoryRequest);

    /**
     * 提供更新目錄的服務
     * @param category 要更新的分類物件
     */
    public void update(Category category);

    /**
     * 依照id刪除分類物件
     * @param id 分類的id
     */
    public void delete(Integer id);

    /**
     * 依照分頁規則返回分頁物件
     * @param pageNum 第幾頁
     * @param pageSize 每頁多少個
     * @return PageInfo物件
     */
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize);
}
