package com.zhiyun.readwrite.controller;/**
 * @Title: WebSocketController
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/6/315:35
 */
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname WebSocketController
 * @Description TODO
 * @Date 2019/6/3 15:35
 * @Created by jiangxing
 */
@RestController
@Component
@Slf4j
public class WebSocketController {

    @RequestMapping("/websocket/{name}")
    public String webSocket(@PathVariable String name, Model model){
        try{
            log.info("跳转到websocket的页面上");
            model.addAttribute("username",name);
            return "websocket";
        }
        catch (Exception e){
            log.info("跳转到websocket的页面上发生异常，异常信息是："+e.getMessage());
            return "error";
        }
    }

}
