package com.zhiyun.readwrite.dao;

import com.zhiyun.readwrite.entity.Devices;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Title: DevicesDao
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/6/1213:31
 */
@Component
@Repository
public interface DevicesDao extends MongoRepository<Devices, String> {
}
