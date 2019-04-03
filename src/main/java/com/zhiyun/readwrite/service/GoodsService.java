package com.zhiyun.readwrite.service;

import com.zhiyun.readwrite.dao.GoodsMapper;
import com.zhiyun.readwrite.entity.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title: GoodsService
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/4/29:49
 */
@Component
@Slf4j
public class GoodsService {
    @Autowired
    private GoodsMapper goodseMapper;

    public List<Goods> list() {
        return goodseMapper.selectByExample(null);
    }

    public int save(Goods goods){
        return goodseMapper.insert(goods);
    }
}
