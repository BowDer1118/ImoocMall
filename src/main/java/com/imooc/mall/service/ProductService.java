package com.imooc.mall.service;

import com.imooc.mall.model.pojo.Product;
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

    /**
     * 更新商品
     * @param updateProduct 要更新的商品
     */
    public void update(Product updateProduct);

    /**
     * 刪除商品
     * @param id 商品的id
     */
    public void delete(Integer id);

    /**
     * 批量更改商品的銷售狀態
     * @param ids 商品的id陣列
     * @param sellStatus 銷售狀態
     */
    public void batchUpdateSellStatus(Integer[] ids,Integer sellStatus);
}
