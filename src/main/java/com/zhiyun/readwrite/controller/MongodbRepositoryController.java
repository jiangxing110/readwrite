package com.zhiyun.readwrite.controller;

import com.zhiyun.readwrite.dao.DevicesDao;
import com.zhiyun.readwrite.entity.Devices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Title: MongodbRepositoryController
 * @ProjectName: readwrite
 * @Description: Repository操作
 * @author: jiangxing
 * @date 2019/6/1116:13
 */
@RestController
@RequestMapping("/mongodbRepository")
public class MongodbRepositoryController {

    @Autowired
    private DevicesDao devicesRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean CreateCustomer(Devices device) {
        devicesRepository.save(device);
        return true;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Devices> List() {
        List<Devices> list = devicesRepository.findAll();
        return list;
    }


}
