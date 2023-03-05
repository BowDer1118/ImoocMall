package com.imooc.mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class AddProductRequest {
    @NotNull(message = "商品名稱不能為空")
    private String name;
    @NotNull(message = "商品圖片不能為空")
    private String image;
    private String detail;
    @NotNull(message = "商品分類不能為空")
    private Integer categoryId;
    @NotNull(message = "商品價格不能為空")
    @Min(value = 0,message ="價格不能小於0")
    private Integer price;
    @NotNull(message = "庫存不能為空")
    @Max(value = 10000,message = "庫存無法超過10000")
    private Integer stock;
    private Integer status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}