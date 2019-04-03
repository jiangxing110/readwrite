package com.zhiyun.readwrite.config;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @Title: DataSourceContextHolder
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/4/29:15
 */

@Component
@Lazy(false)
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
