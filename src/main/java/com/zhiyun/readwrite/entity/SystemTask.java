package com.zhiyun.readwrite.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 系统任务实体类
 *
 * @author 邓艺
 * @version v1.0
 * @date 2018-12-27 16:58
 */
@Data
public class SystemTask {
    /**
     * 搬运任务唯一的表示id
     */
    private Integer id;
    /**
     * 开始节点id
     */
    private String start;
    /**
     * 结束节点id
     */
    private String end;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 请求时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
    private Date startTime;
    /**
     * 是否完成
     */
    private String isFinished;
    /**
     * 完成时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
    private Date finishedTime;
    /**
     * 消息描述
     */
    private String message;
}
