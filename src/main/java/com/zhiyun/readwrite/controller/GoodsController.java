package com.zhiyun.readwrite.controller;

import com.zhiyun.readwrite.base.BaseResult;
import com.zhiyun.readwrite.entity.Goods;
import com.zhiyun.readwrite.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: GoodsController
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/4/29:56
 */
@Slf4j
@RestController
@RequestMapping("/Goods")
@Api(tags = "读写分离主从复制")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/findAll")
    @ApiOperation(value = "查询全部--只读数据库")
    public BaseResult<List<Goods>> findAll() {
        BaseResult<List<Goods>> baseResult = new BaseResult<List<Goods>>();
        baseResult.setData(goodsService.list());
        baseResult.setMessage("查询信息成功");
        baseResult.setResult(true);
        return baseResult;
    }

    @ApiOperation(value = "新增编辑接口--只写数据库")
    @RequestMapping(value = "Save", method = {RequestMethod.POST, RequestMethod.PUT})
    public BaseResult<Goods> Save(@RequestBody Goods goods) {
        BaseResult<Goods> baseResult = new BaseResult<Goods>();
        int i = goodsService.save(goods);
        if (i == 1) {
            baseResult.setData(goods);
            baseResult.setMessage("保存成功");
        } else {
            baseResult.setData(null);
            baseResult.setMessage("保存失败");
        }
        return baseResult;
    }
}
