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
}
