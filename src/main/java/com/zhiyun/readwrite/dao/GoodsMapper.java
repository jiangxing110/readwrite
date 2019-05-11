package com.zhiyun.readwrite.dao;

import com.zhiyun.readwrite.entity.Goods;
import com.zhiyun.readwrite.entity.GoodsExample;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GoodsMapper {
    long countByExample(GoodsExample example);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExample(GoodsExample example);
}