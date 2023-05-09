package com.lagou.controller;

import com.lagou.domain.ResourceCategory;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/ResourceCategory")
public class ResourceCategoryController {

    @Autowired
    private ResourceCategoryService resourceCategoryService;

    /*
    *   查询所有资源分类信息
    * */
    @RequestMapping(path = "/findAllResourceCategory")
    public ResponseResult findAllResourceCategory() {

        List<ResourceCategory> allResourceCategory = resourceCategoryService.findAllResourceCategory();

        return new ResponseResult(true, 200, "查询所有分类信息成功", allResourceCategory);

    }
}
