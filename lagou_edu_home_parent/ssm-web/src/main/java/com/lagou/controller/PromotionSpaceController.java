package com.lagou.controller;

import com.lagou.domain.PromotionSpace;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/PromotionSpace")
public class PromotionSpaceController {

    @Autowired
    private PromotionSpaceService promotionSpaceService;

    @RequestMapping(path = "findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace() {

        List<PromotionSpace> list = promotionSpaceService.findAllPromotionSpace();

        ResponseResult responseResult = new ResponseResult(true, 200, "获取所有广告位成功", list);

        return responseResult;

    }

    @RequestMapping(path = "/saveOrUpdatePromotionSpace")
    public ResponseResult saveOrUpdatePromotionSpace(@RequestBody PromotionSpace promotionSpace) {

        // 判断是否有promotionSpace的id值，有则是修改，没有则是新增
        if (promotionSpace.getId() == null) {

            promotionSpaceService.savePromotionSpace(promotionSpace);

            return new ResponseResult(true, 200, "新增广告位成功", null);

        } else {

            promotionSpaceService.updatePromotionSpace(promotionSpace);

            return new ResponseResult(true, 200, "修改广告位成功", null);

        }

    }

    /*
    *   回显广告位名称
    * */
    @RequestMapping(path = "/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(Integer id) {

        PromotionSpace promotionSpaceById = promotionSpaceService.findPromotionSpaceById(id);

        return new ResponseResult(true, 200, "回显广告位名称成功", promotionSpaceById);

    }

}
