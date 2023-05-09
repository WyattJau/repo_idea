package com.lagou.service.impl;

import com.lagou.dao.PromotionSpaceMapper;
import com.lagou.domain.PromotionSpace;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PromotionSpaceServiceImpl implements PromotionSpaceService {

    @Autowired
    private PromotionSpaceMapper promotionSpaceMapper;

    @Override
    public List<PromotionSpace> findAllPromotionSpace() {

        List<PromotionSpace> list = promotionSpaceMapper.findAllPromotionSpace();

        return list;
    }

    @Override
    public void savePromotionSpace(PromotionSpace promotionSpace) {

        // 补全信息
        UUID uuid = UUID.randomUUID();
        Date date = new Date();

        promotionSpace.setSpaceKey(uuid.toString());
        promotionSpace.setCreateTime(date);
        promotionSpace.setUpdateTime(date);
        promotionSpace.setIsDel(0);

        // 调用dao
        promotionSpaceMapper.savePromotionSpace(promotionSpace);

    }

    @Override
    public void updatePromotionSpace(PromotionSpace promotionSpace) {

        // 补全信息
        promotionSpace.setUpdateTime(new Date());

        promotionSpaceMapper.updatePromotionSpace(promotionSpace);

    }

    @Override
    public PromotionSpace findPromotionSpaceById(Integer id) {

        PromotionSpace promotionSpace = promotionSpaceMapper.findPromotionSpaceById(id);

        return promotionSpace;

    }

}
