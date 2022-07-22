package com.yjb.goods.controller;

import com.yjb.goods.annotation.IsLogin;
import com.yjb.goods.annotation.RepeatSubmit;
import com.yjb.goods.biz.impl.GoodServiceImpl;
import com.yjb.goods.pojo.Good;
import com.yjb.goods.pojo.GoodQuery;
import com.yjb.goods.pojo.User;

import com.yjb.goods.util.DateUtil;
import com.yjb.goods.util.GlobalSessionConstants;
import com.yjb.goods.util.Result;
import com.yjb.goods.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
@Slf4j(topic = "recommend")
public class GoodController {

    @Autowired
    private GoodServiceImpl goodService;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    //重启项目时可能出现数据拉不起来情况，所以建议缓存数据
    @GetMapping("page/{pageNo}/size/{size}")
    @CrossOrigin
    @IsLogin
    public Page getPage1(@PathVariable("pageNo") Integer pageNo,@PathVariable("size") Integer size){

        Page<Good> goodNoCriteria = goodService.findGoodNoCriteria(pageNo,size);
        redisTemplate.opsForValue().set(pageNo + "页", goodNoCriteria,5000, TimeUnit.SECONDS);
        return goodNoCriteria;

    }


    @GetMapping("page2/{pageNo}/size/{size}")
    @CrossOrigin
    @RepeatSubmit
    @IsLogin
    public Page getPage2(@PathVariable("pageNo") Integer pageNo,@PathVariable("size") Integer size, GoodQuery goodQuery){
        //Map<String, Object> goodMap = new HashMap<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //如果用户所有的输入都为空，查询全部数据
        if (goodQuery.getId() == null && goodQuery.getName() == ""
                && goodQuery.getLessPrice() =="" && goodQuery.getMorePrice() == ""){
            //如果缓存中存在应该从缓存中取出
            Page ma = (Page)redisTemplate.opsForValue().get(pageNo + "页");
            if (ma == null || ma.isEmpty()){
                Page<Good> goodNoCriteria = goodService.findGoodNoCriteria(pageNo, size);
                redisTemplate.opsForValue().set(pageNo + "页", goodNoCriteria,2000, TimeUnit.SECONDS);
                return goodNoCriteria;
            }
            return ma;
        }
        //从mysql中拉取数据，此接口限制重复提交
        Page<Good> goodCriteria = goodService.findGoodCriteria(pageNo,size,goodQuery);


            //缓存当页所以的商品
//            Iterator<Good> iterator = goodCriteria.iterator();
//            for (Iterator<Good> it = iterator; it.hasNext(); ) {
//                Good g = it.next();
//                goodMap.put(g.getId().toString(), g);
//            }
//            goodMap.put("totalPages",goodCriteria.getTotalPages());
//            goodMap.put("totalElements",goodCriteria.getTotalElements());

            return goodCriteria;
    }

    //处理单个删除
    @DeleteMapping("delete/{gid}")
    @CrossOrigin
    @IsLogin
    public Result deleteGood(@PathVariable("gid") Integer gid){

        String currentTime = DateUtil.getCurrentTime();
        goodService.deleteGood(new Good(gid));
        Result<Integer> gidResult = Result.bulidResult(Status.OK, gid);
        log.info("Product {}. deleted successfully Time {}.",gid,currentTime);
        return gidResult;
    }

    //增加或者修改数据
    @PutMapping("save")
    @CrossOrigin
    @IsLogin
    public Result addOrUpdate(HttpServletRequest request, @RequestBody  Good good){
        String currentTime = DateUtil.getCurrentTime();
        String username = request.getParameter("username");
        User u = (User)redisTemplate.opsForValue().get(username);
        //获取当前登陆用户的id
        String uid = u.getId().toString();
        if (good.getId() == null){
            //新增商品
            good.setUser_id(uid);
            goodService.addOrSaveGood(good);
            Result<Good> goodResult = Result.bulidResult(Status.OK, good);

            log.info("Product {}. added successfully Time {}.",good,currentTime);
            return goodResult;
        }
        Good g = goodService.findById(good.getId());
        g.setDescrip(good.getDescrip());
        g.setHeigth(good.getHeigth());
        g.setLength(good.getLength());
        g.setName(good.getName());
        g.setPrice(good.getPrice());
        g.setDel(good.getDel());
        g.setWidth(good.getWidth());
        g.setImg(good.getImg());
        g.setWeigth(good.getWeigth());
        g.setModify_time(DateUtil.getCurrentTimeStamp());
        g.setUser_id(uid);
        Good good1 = goodService.addOrSaveGood(g);
        Result<Good> updateResult = Result.bulidResult(Status.OK, good1);
        log.info("Product {}. modification  successfully Time {}.",good1,currentTime);
        return updateResult;

    }
}
