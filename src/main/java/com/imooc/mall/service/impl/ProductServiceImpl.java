package com.imooc.mall.service.impl;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductRequest;
import com.imooc.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    /**
     * 加入商品
     *
     * @param addProductRequest 要加入的商品資訊
     */
    @Override
    public void add(AddProductRequest addProductRequest) {
        Product product=new Product();
        BeanUtils.copyProperties(addProductRequest,product);
        //排除商品重複命名問題
        Product productOld = productMapper.selectProductByName(product.getName());
        if(productOld!=null){
            throw new ImoocMallException(ImoocMallExceptionEnum.PRODUCT_NAME_EXIST);
        }
        //插入資料
        int count=productMapper.insertSelective(product);
        if(count!=1){
            throw new ImoocMallException(ImoocMallExceptionEnum.PRODUCT_ADD_FAILED);
        }
    }

    /**
     * 更新商品
     *
     * @param updateProduct 要更新的商品
     */
    @Override
    public void update(Product updateProduct) {
        Product productOld=productMapper.selectProductByName(updateProduct.getName());
        //同名不同id不可修改
        if(productOld!=null&& (!productOld.getId().equals(updateProduct.getId()))){
            throw new ImoocMallException(ImoocMallExceptionEnum.PRODUCT_UPDATE_FAILED);
        }
        int count=productMapper.updateByPrimaryKeySelective(updateProduct);
        if(count!=1){
            throw new ImoocMallException(ImoocMallExceptionEnum.PRODUCT_UPDATE_FAILED);
        }
    }

    /**
     * 刪除商品
     *
     * @param id 商品的id
     */
    @Override
    public void delete(Integer id) {
        Product productOld=productMapper.selectByPrimaryKey(id);
        //查不到該紀錄無法刪除
        if(productOld==null){
            throw new ImoocMallException(ImoocMallExceptionEnum.PRODUCT_DELETE_FAILED);
        }
        int count=productMapper.deleteByPrimaryKey(id);

        if(count!=1){
            throw new ImoocMallException(ImoocMallExceptionEnum.PRODUCT_DELETE_FAILED);
        }
    }

    /**
     * 批量更改商品的銷售狀態
     *
     * @param ids        商品的id陣列
     * @param sellStatus 銷售狀態
     */
    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) {
         productMapper.batchUpdateSellStatus(ids,sellStatus);
    }
}
