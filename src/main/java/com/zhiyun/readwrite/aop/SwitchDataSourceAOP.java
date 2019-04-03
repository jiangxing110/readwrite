package com.zhiyun.readwrite.aop;

import com.zhiyun.readwrite.config.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Title: SwitchDataSourceAOP
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/4/29:17
 */
@Aspect
@Component
@Lazy(false)
@Order(0) //Order设定AOP执行顺序 使之在数据库事务上先
@Slf4j
public class SwitchDataSourceAOP {
    @Before("execution(* com.zhiyun.readwrite.service.*.*(..))")
    public void process(JoinPoint joinPoint) {
        String methodName=joinPoint.getSignature().getName();
        if (methodName.startsWith("get")
                ||methodName.startsWith("count")
                ||methodName.startsWith("find")
                ||methodName.startsWith("list")
                ||methodName.startsWith("select")
                ||methodName.startsWith("check")){
            log.info("切换51数据库");
            DataSourceContextHolder.setDbType("selectDataSource");
        }else {
            //切换dataSource
            log.info("切换42数据库");
            DataSourceContextHolder.setDbType("updateDataSource");
        }
    }
}
