package com.zhiyun.readwrite.controller;

import com.zhiyun.readwrite.base.BaseResult;
import com.zhiyun.readwrite.entity.SystemTask;
import com.zhiyun.readwrite.service.ApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Title: ApiController
 * @ProjectName: readwrite
 * @Description: Api测试接口
 * @author: jiangxing
 * @date 2019/5/1314:26
 */
@RestController
@RequestMapping("Api")
@Api(tags = "Api测试接口")
@Slf4j
public class ApiController {

    @Autowired
    private ApiService apiService;

    /**
     * @Description: 审批
     * @Param: []
     * @return: com.zhiyun.common.utils.R
     * @Author: jiangxing
     * @Date: 2019/4/13 10:53
     */
    @RequestMapping(value = "/send", method = {RequestMethod.POST})
    @ApiOperation(value = "质检审批")
    public BaseResult<String> sendMessage(SystemTask systemTask) {
        BaseResult<String> baseResult = new BaseResult<String>();
        try {
            systemTask.setStartTime(new Date());
            Thread.sleep(5000);
            systemTask.setFinishedTime(new Date());
            String msg = apiService.sendMessage(systemTask);
            baseResult.setData(msg);
        } catch (Exception rre) {
            log.debug("业务异常" + rre.getMessage());
            baseResult.setResult(false);
            baseResult.setMessage(rre.getMessage());
        }
        return baseResult;
    }


}
