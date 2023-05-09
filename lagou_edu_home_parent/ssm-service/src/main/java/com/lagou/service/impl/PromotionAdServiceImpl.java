package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.PromotionAdMapper;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PromotionAdServiceImpl implements PromotionAdService {

    @Autowired
    private PromotionAdMapper promotionAdMapper;

    /*
    *   分页查询广告信息
    * */
    @Override
    public PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdVO promotionAdVO) {

        PageHelper.startPage(promotionAdVO.getCurrentPage(), promotionAdVO.getPageSize());
        List<PromotionAd> list = promotionAdMapper.findAllPromotionAdByPage();

        PageInfo<PromotionAd> pageInfo = new PageInfo<>(list);

        return pageInfo;


    }

    @Override
    public void savePromotionAd(PromotionAd promotionAd) {

        // 封装数据
        Date date = new Date();
        promotionAd.setCreateTime(date);
        promotionAd.setUpdateTime(date);

        promotionAdMapper.savePromotionAd(promotionAd);

    }

    @Override
    public void updatePromotionAd(PromotionAd promotionAd) {

        // 封住数据
        promotionAd.setUpdateTime(new Date());

        promotionAdMapper.updatePromotionAd(promotionAd);

    }

    @Override
    public PromotionAd findPromotionAdById(Integer id) {

        PromotionAd promotionAdById = promotionAdMapper.findPromotionAdById(id);

        return promotionAdById;

    }

    @Override
    public void updatePromotionAdStatus(Integer id, Integer status) {

        PromotionAd promotionAd = new PromotionAd();
        promotionAd.setId(id);
        promotionAd.setStatus(status);
        promotionAd.setUpdateTime(new Date());

        promotionAdMapper.updatePromotionAdStatus(promotionAd);

    }
}
