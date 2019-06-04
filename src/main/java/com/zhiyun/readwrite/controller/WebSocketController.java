package com.zhiyun.readwrite.controller;
/**
 * @Title: WebSocketController
 * @ProjectName: readwrite
 * @Description: 聊天室
 * @author: jiangxing
 * @date 2019/6/315:35
 */
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.zhiyun.readwrite.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Classname WebSocketController
 * @Description TODO
 * @Date 2019/6/3 15:35
 * @Created by jiangxing
 */
@RestController
@Component
@Slf4j
@RequestMapping("/ws")
public class WebSocketController {

    public static Map<Long, String> img=new HashMap();
    /**
     * 图片保存路径
     */
    private String imgPath = new ApplicationHome(getClass()).getSource().getParentFile().toString()+"/img/";

    /**
     * 获取所有房间
     * @return
     */
    @RequestMapping("/allRoom")
    public Map<String,Object> allRoom(){
        Map<String,Object> result = new HashMap<>();
        HashMap<String, CopyOnWriteArraySet<User>> userForRoom = WebSocket.UserForRoom;
        List<String> rooms = new ArrayList<>();
        for (String key : userForRoom.keySet()) {
            rooms.add(key);
        }
        result.put("rooms",rooms);
        return result;
    }

    /**
     * 判断昵称在某个房间中是否已存在，房间是否有密码，如果有，用户输入的密码又是否正确
     * @param room 房间号
     * @param nick 昵称
     * @param pwd 密码
     * @return
     */
    @RequestMapping("/judgeNick")
    public Map<String,Object> judgeNick(String room, String nick, String pwd){
        Map<String,Object> result = new HashMap<>();
        result.put("code",0);
        CopyOnWriteArraySet<User> rooms = WebSocket.UserForRoom.get(room);
        if (rooms != null){
            rooms.forEach(user -> {
                if (user.getNickname().equals(nick)){
                    result.put("code",1);
                    result.put("msg","昵称已存在，请重新输入");
                    log.debug("有重复");
                }
            });
            if ((Integer)result.get("code") != 0){
                return result;
            }
            String password = WebSocket.PwdForRoom.get(room);
            if (StrUtil.isNotEmpty(password) && !(pwd.equals(password))){
                result.put("code",2);
                result.put("msg","密码错误，请重新输入");
                return result;
            }else {
                result.put("code",3);
                result.put("msg","房间无密码");
                return result;
            }
        }
        return result;
    }

    /**
     * 获得当前房间中的所有用户
     */

    @RequestMapping("/online")
    public Map<String,Object> online(String room){
        Map<String,Object> result = new HashMap<>();
        CopyOnWriteArraySet<User> rooms = WebSocket.UserForRoom.get(room);
        List<Map<String,String>> users = new ArrayList<>();
        if (rooms != null){
            rooms.forEach(user -> {
                Map<String,String> map = new HashMap<>();
                map.put("nick",user.getNickname());
                map.put("id",user.getId());
                users.add(map);
            });
            result.put("onlineNum",rooms.size());
            result.put("onlineUsera",users);
        }else {
            result.put("onlineNum",0);
            result.put("onlineUsera",null);
        }
        return result;
    }

    /**
     * 实现文件上传
     * */
    @RequestMapping("/fileUpload")
    public Map<String,Object> fileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file){
        Map<String,Object> result = new HashMap<>();
        //获取项目访问路径
        String root = request.getRequestURL().toString().replace(request.getRequestURI(),"");
        String proName=request.getServerName();
        if(file.isEmpty()){
            return null;
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        //重命名文件
        String imgName = RandomUtil.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        log.debug("上传图片保存在：" + imgPath + imgName);
        File dest = new File(imgPath + imgName);
        img.put(System.currentTimeMillis(),imgPath + imgName);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            //返回图片访问路径
            result.put("url",root+"/readwrite" +"/img/" + imgName);
            log.debug("图片保存成功，访问路径为："+result.get("url"));
            return result;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            log.error("图片保存失败！");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("图片保存失败！");
        }
        return null;
    }

}
