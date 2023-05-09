package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/PromotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;

    @RequestMapping(path = "/findAllPromotionAdByPage")
    public ResponseResult findAllAdByPage(PromotionAdVO promotionAdVO) {

        PageInfo<PromotionAd> allPromotionAdByPage = promotionAdService.findAllPromotionAdByPage(promotionAdVO);

        ResponseResult responseResult = new ResponseResult(true, 200, "查找分页数据成功", allPromotionAdByPage);

        return responseResult;

    }

    /*
     *   广告图片上传
     * */
    @RequestMapping(path = "/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {

        // 1、判断接收到的文件是否为空
        if (file.isEmpty()) {
            throw new RuntimeException();
        }

        // 2、获取项目的部署路径  D:\study\JavaWeb\tomcat\apache-tomcat-8.5.55-windows-x64\apache-tomcat-8.5.55\webapps\ssm_web
        String realPath = request.getServletContext().getRealPath("/");
        // 截取路径 截成D:\study\JavaWeb\tomcat\apache-tomcat-8.5.55-windows-x64\apache-tomcat-8.5.55\webapps\
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));

        // 3、获取原文件名
        // 比如原文件名为：test.jpg
        String originalFilename = file.getOriginalFilename();

        // 4、生成新文件名 System.currentTimeMillis()获取当前时间戳 originalFilename.substring(originalFilename.indexOf("."))将文件名中.号前面内容截掉，只获取后缀名
        // 假设此处时间戳为1234，那么新文件名为 1234.jpg
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 5、文件上传
        String uploadPath = substring + "upload\\";
        File filePath = new File(uploadPath, newFileName);

        // 如果目录不存在就创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录：" + filePath);
        }

        file.transferTo(filePath);

        // 6、将文件名和文件路径返回，进行响应
        Map<String, String> map = new HashMap<>();
        map.put("fileName", newFileName);
        // "filePath": "https://localhost:8080/upload/12352353.JPG"
        map.put("filePath", "https://localhost:8080/upload/" + newFileName);

        ResponseResult responseResult = new ResponseResult(true, 200, "上传成功", map);

        return responseResult;

    }

    /*
    *   新建或修改广告
    * */
    @RequestMapping(path = "saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd) {

        if (promotionAd.getId() == null) {
            promotionAdService.savePromotionAd(promotionAd);

            return new ResponseResult(true, 200, "新建广告成功", null);
        } else {
            promotionAdService.updatePromotionAd(promotionAd);

            return new ResponseResult(true, 200, "修改广告成功", null);
        }
    }

    /*
    *   回显广告信息
    * */
    @RequestMapping(path = "/findPromotionAdById")
    public ResponseResult findPromotionAdById(Integer id) {

        PromotionAd list = promotionAdService.findPromotionAdById(id);

        ResponseResult responseResult = new ResponseResult(true, 200, "回显广告成功", list);

        return responseResult;

    }

    /*
    *   广告动态上下线
    * */
    @RequestMapping(path = "/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(Integer id, Integer status) {

        // 调用service
        promotionAdService.updatePromotionAdStatus(id, status);

        // 建立map集合
        Map<Object, Object> map = new HashMap<>();
        map.put("status", status);

        // 返回ResponseResult
        return new ResponseResult(true, 200, "广告状态修改成功", map);

    }

}
