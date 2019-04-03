package com.zhiyun.readwrite.base;

import lombok.Data;

/**
 * 基础返回信息封装类
 *
 * @author 邓艺
 * @version v1.0
 * @date 2018-12-27 15:16
 */
@Data
public class BaseResult<T> {
    /**
     * 执行状态
     */
    private Boolean result = true;
    /**
     * 执行信息
     */
    private String message = "成功";
    /**
     * 数据体
     */
    private T data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
