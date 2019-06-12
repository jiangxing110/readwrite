package com.zhiyun.readwrite.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @Title: Device
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/6/1210:45
 */
@Data
public class Devices {
    /**
     *
     */
    @Id
    private Long id;
    /**
     * 设备编码
     */
    private String code;
    /**
     * 设备名称
     */
    private String name;

    /**
     * 机器猫编码
     */
    private String mcatNo;
    /**
     * 车间id
     */

    /**
     * 品牌
     */
    private String brand;
    /**
     * 规格
     */

    /**
     * 型号
     */
    private String model;
    /**
     * 出产日期
     */

    /**
     * 备注
     */
    private String remark;


}
