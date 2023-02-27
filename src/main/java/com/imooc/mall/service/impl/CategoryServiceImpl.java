package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CategoryMapper;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryRequest;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    /**
     * 提供目錄新增的服務
     *
     * @param addCategoryRequest 要新增的目錄資訊
     */
    @Override
    public void add(AddCategoryRequest addCategoryRequest) {
        Category category=new Category();
        //使用Spring提供的copyProperties將屬性名稱相同的屬性複製到另一個物件中
        BeanUtils.copyProperties(addCategoryRequest,category);
        //檢查分類的名稱是否重名
        Category categoryOld=categoryMapper.selectByName(addCategoryRequest.getName());
        if(categoryOld!=null){
            throw new ImoocMallException(ImoocMallExceptionEnum.CATEGORY_NAME_EXIST);
        }
        int count=categoryMapper.insertSelective(category);
        //檢查插入結果
        if(count!=1){
            throw new ImoocMallException(ImoocMallExceptionEnum.CATEGORY_NAME_EXIST);
        }
    }

    /**
     * 提供更新目錄的服務
     *
     * @param updateCategory 要更新的分類物件
     */
    @Override
    public void update(Category updateCategory) {
        //排除重名但不同id
        if(updateCategory.getName()!=null){
            Category categoryOld=categoryMapper.selectByName(updateCategory.getName());
            if(categoryOld!=null && (!(categoryOld.getId().equals(updateCategory.getId())))){
                throw new ImoocMallException(ImoocMallExceptionEnum.CATEGORY_NAME_EXIST);
            }
        }

        int count=categoryMapper.updateByPrimaryKeySelective(updateCategory);
        if(count!=1){
            throw new ImoocMallException(ImoocMallExceptionEnum.CATEGORY_UPDATE_FAILED);
        }
    }

    /**
     * 依照id刪除分類物件
     *
     * @param id 分類的id
     */
    @Override
    public void delete(Integer id) {
        //檢查是否存在分類物件
        Category category=categoryMapper.selectByPrimaryKey(id);
        if(category==null){
            throw new ImoocMallException(ImoocMallExceptionEnum.CATEGORY_DELETE_FAILED);
        }
        //刪除分類
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 依照分頁規則返回分頁物件
     * @param pageNum 第幾頁
     * @param pageSize 每頁多少個
     * @return PageInfo物件
     */
    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        //分頁並依照type和order_num排序
        PageHelper.startPage(pageNum,pageSize,"type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        //依照分頁規則對categoryList進行分頁
        PageInfo pageInfo=new PageInfo(categoryList);
        return pageInfo;
    }

    /**
     * 獲取分類列表
     *
     * @return 分類列表的List
     */
    @Override
    @Cacheable(value="listCategoryForCustomer") //在Redis中key的名字
    public List<CategoryVO> listCategoryForCustomer() {
        ArrayList<CategoryVO> categoryVOList=new ArrayList<>();
        recursivelyFindCategories(categoryVOList,0);
        return categoryVOList;
    }

    /**
     * Helper Function
     * 使用遞迴技巧為每一層級的分類填入資料
     * @param categoryVOList 存放分類物件的列表
     * @param parentId 分類的上級目錄
     */
    private void recursivelyFindCategories(List<CategoryVO> categoryVOList,Integer parentId){
        //遞迴獲得所有子類別並組合成目錄樹
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if(!CollectionUtils.isEmpty(categoryList)){
            for (int i = 0; i < categoryList.size(); i++) {
                Category category =  categoryList.get(i);
                CategoryVO categoryVO=new CategoryVO();
                BeanUtils.copyProperties(category,categoryVO);
                categoryVOList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory(),categoryVO.getId());
            }
        }
    }
}
