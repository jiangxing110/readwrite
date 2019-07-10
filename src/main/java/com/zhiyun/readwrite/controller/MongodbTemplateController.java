package com.zhiyun.readwrite.controller;

import com.alibaba.fastjson.JSON;
import com.zhiyun.readwrite.entity.Goods;
import com.zhiyun.readwrite.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Title: MongodbController
 * @ProjectName: readwrite
 * @Description: MongodbTemplate 操作
 * @author: jiangxing
 * @date 2019/6/1114:41
 */

@RestController
@RequestMapping("/mongodb")
public class MongodbTemplateController {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**表名*/
    private static final String collectionName = "goods";

    /**
     * 描述：新增
     * @author maochengyuan
     * @created 2018/9/1 20:17
     * @param goods
     * @return ResultObject
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public int insert( Goods goods) throws Exception {
        String a=null;
        this.mongoTemplate.insert(goods,collectionName);
        return HttpServletResponse.SC_OK;
    }

    /**
     * 描述：删除
     * @author maochengyuan
     * @created 2018/9/1 20:17
     * @param id
     * @return ResultObject
     */
    @RequestMapping("/delete")
    @ResponseBody
    public int delete( long id) throws Exception {
        Query query = Query.query(Criteria.where("_id").is(id));
        this.mongoTemplate.remove(query, collectionName);
        return  HttpServletResponse.SC_OK;
    }

    /**
     * 描述：修改
     * @author maochengyuan
     * @created 2018/9/1 20:17
     * @param goods
     * @return ResultObject
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public int update( Goods goods) throws Exception {
        Query query = Query.query(Criteria.where("_id").is(goods.getId()));
        Update update = new Update();
        update.set("_id", goods.getId());
        update.set("name", goods.getName());
        update.set("stock", goods.getStock());
        this.mongoTemplate.updateFirst(query, update, collectionName);
        return (HttpServletResponse.SC_OK);
    }

    /**
     * 描述：查询
     * @author maochengyuan
     * @created 2018/9/1 20:17
     * @param
     * @return ResultObject
     */
    @RequestMapping("/query")
    @ResponseBody
    public int query() throws Exception {
        String name="料筒";
        //正则  name是你需要输入的查询值
        //完全匹配
        Pattern pattern = Pattern.compile("^"+name+"$", Pattern.CASE_INSENSITIVE);
//右匹配
        Pattern pattern1 = Pattern.compile("^.*"+name+"$", Pattern.CASE_INSENSITIVE);
//左匹配
        Pattern pattern2 = Pattern.compile("^"+name+"*$", Pattern.CASE_INSENSITIVE);
//模糊匹配
        Pattern pattern3 = Pattern.compile("^.*"+name+".*$", Pattern.CASE_INSENSITIVE);
        Pattern pattern4 = Pattern.compile("^.*" + "料筒" + ".*$");
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(pattern4));

        List<Goods> users = this.mongoTemplate.find(query, Goods.class);
        System.err.println(users);
        return (HttpServletResponse.SC_OK);
    }

}