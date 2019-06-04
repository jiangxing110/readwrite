package com.zhiyun.readwrite.config;

import com.zhiyun.readwrite.controller.WebSocketController;
import com.zhiyun.readwrite.utils.BingImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * @Title: QuartzService
 * @ProjectName: readwrite
 * @Description: 定时任务配置
 * @author: jiangxing
 * @date 2019/6/410:37
 */
@Component
@Slf4j
public class QuartzService {

    /**
     * 定期删除聊天图片
     */
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void timerToNow(){
        log.debug("开始查询需要删除的图片。。。。。。。。。。");
        Map<Long,String> img = WebSocketController.img;
        Long now = System.currentTimeMillis()-60000;
        Iterator<Map.Entry<Long, String>> it = img.entrySet().iterator();
        int a = 0;
        while(it.hasNext()){
            Map.Entry<Long, String> entry = it.next();
            if (entry.getKey() < now){
                if (deleteFile(entry.getValue())){
                    it.remove();
                    a++;
                }
            }
        }
        log.debug("删除任务执行完毕，共删除"+a+"张图片");
    }
    /**
     * 定期下载必应壁纸,每天中午12点触发
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void dowBingImage(){
        log.debug("开始同步必应壁纸。。。。。。。。。。");
        Integer i = BingImageUtil.download(0,1);
        log.debug("本次同步了"+i+"张壁纸！");
    }

    private boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.debug("图片"+fileName+"删除成功");
                return true;
            } else {
                log.debug("图片"+fileName+"删除成功");
                return false;
            }
        } else {
            log.debug("图片"+fileName+"删除失败");
            return false;
        }

    }

}
