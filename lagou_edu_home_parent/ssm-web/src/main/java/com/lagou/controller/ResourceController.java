package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(path = "findAllResource")
    public ResponseResult findAllResourceByPage(@RequestBody ResourceVo resourceVo) {

        PageInfo<Resource> pageInfo = resourceService.findAllResourceByPage(resourceVo);

        ResponseResult responseResult = new ResponseResult(true, 200, "资源信息分页多条件查询成功", pageInfo);

        return responseResult;

    }

}
