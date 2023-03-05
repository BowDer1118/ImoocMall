package com.imooc.mall.service;

import com.imooc.mall.model.request.AddProductRequest;

/**
 * 提供商品相關的服務
 */
public interface ProductService {
    /**
     * 加入商品
     * @param addProductRequest 要加入的商品資訊
     */
    public void add(AddProductRequest addProductRequest);
}
