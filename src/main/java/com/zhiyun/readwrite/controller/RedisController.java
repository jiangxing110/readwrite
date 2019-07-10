package com.zhiyun.readwrite.controller;


import com.zhiyun.readwrite.config.RedisConfig;
import com.zhiyun.readwrite.entity.Goods;
import com.zhiyun.readwrite.service.GoodsService;

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Title: RedisController
 * @ProjectName: readwrite
 * @Description: RedisController
 * @author: jiangxing
 * @date 2019/6/68:46
 */
@Slf4j
@RestController
@RequestMapping("/Redis")
@Api(tags = "redis_crud")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goods")
    @ApiOperation(value = "查询全部--只读数据库")
    public List<Goods> goods() {
        //查询缓存
        redisTemplate = RedisConfig.initRedis(0,redisTemplate);
        String aa=(String) redisTemplate.opsForValue().get("aaa");
        log.error(aa);
        /*List<Goods> studentList= (List<Goods>)redisTemplate.opsForValue().get("allStudents");
        if(null == studentList) {
            //缓存为空，查询一遍数据库
            List<Goods> goods=goodsService.list();
            //把数据库查询出来数据，放入Redis中
            redisTemplate.opsForValue().set("goods",goods);

        }*/
        redisTemplate = RedisConfig.initRedis(9,redisTemplate);
        String a=(String) redisTemplate.opsForValue().get("aCarStatus");
        log.error(a);
        redisTemplate = RedisConfig.initRedis(10,redisTemplate);
        String b=(String) redisTemplate.opsForValue().get("bCarStatus");
        log.error(b);
        return null;
    }
}
