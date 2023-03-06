package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.request.AddProductRequest;
import com.imooc.mall.model.request.UpdateProductRequest;
import com.imooc.mall.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * 後台商品管理Controller
 */

@RestController
public class ProductAdminController {
    @Resource
    private ProductService productService;

    /**
     * 添加商品
     * @param addProductRequest 商品的資訊
     * @return ApiRestResponse
     */
    @PostMapping("admin/product/add")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductRequest addProductRequest){
        productService.add(addProductRequest);
        return ApiRestResponse.success();
    }

    /**
     * 上傳圖片
     * @param httpServletRequest Request
     * @param file 上傳的檔案物件
     * @return ApiRestResponse
     */
    @PostMapping("admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file){
        //獲取檔名
        String fileName=file.getOriginalFilename();
        //檔案副檔名
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        //利用UUID重命名文件名稱
        UUID uuid=UUID.randomUUID();
        String newFileName=uuid.toString()+suffixName;
        //創建資料夾
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        //建立檔案
        File destFile=new File(Constant.FILE_UPLOAD_DIR+newFileName);
        //檢查資料夾是否存在
        if(!fileDirectory.exists()){
            //檢查建立狀態
            if (!fileDirectory.mkdir()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.FILE_UPLOAD_FAILED);
            }
        }
        //將上傳的圖片保存
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImoocMallException(ImoocMallExceptionEnum.FILE_UPLOAD_FAILED);
        }
        //返回圖片的地址
        try {
            //使用httpServletRequest.getRequestURL()獲取URL的StringBuffer並交給URI構造函數解析
            return ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURL()+""))+"/images/"+newFileName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ApiRestResponse.error(ImoocMallExceptionEnum.FILE_UPLOAD_FAILED);
    }

    /**
     * 獲取主機URI資訊
     * @param uri 傳入的資訊
     * @return URI物件
     */
    private URI getHost(URI uri){
        URI effectiveURI;
        try {
            /**
             * uri.getScheme() 獲取協定:http https...
             * uri.getUserInfo() 獲取用戶訊息:帳號密碼...
             * uri.getHost() 獲得主機ip
             * uri.getPort() 獲得主機端口號
             */
            effectiveURI=new URI(uri.getScheme(),uri.getUserInfo(), uri.getHost(), uri.getPort(), null,null,null);
        } catch (URISyntaxException e) {
            effectiveURI=null;
        }
        return effectiveURI;
    }
    @PostMapping("/admin/product/update")
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductRequest updateProductRequest){
        return ApiRestResponse.success();
    }
}
