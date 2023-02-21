package com.imooc.mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 封裝AddCategory Request
 * 所傳送的data資料
 * 使用@Size,@NotNull對屬性進行限制與驗證
 */
public class AddCategoryRequest {
    @Size(min = 2,max = 5,message = "name必須介於[2,5]之間") //message是當驗證失敗所要輸出的訊息
    @NotNull(message = "name不能為空")
    private String name;

    @NotNull(message = "type不能為空")
    @Max(value = 3,message = "type必須介於[1,3]之間")
    private Integer type;
    @NotNull(message = "parentId不能為空")
    private Integer parentId;
    @NotNull(message = "orderNum不能為空")
    private Integer orderNum;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
