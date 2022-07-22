package com.yjb.goods.controller;

import com.yjb.goods.annotation.IsLogin;
import com.yjb.goods.annotation.RepeatSubmit;
import com.yjb.goods.biz.impl.UserServiceImpl;
import com.yjb.goods.pojo.User;
import com.yjb.goods.util.DateUtil;
import com.yjb.goods.util.GlobalSessionConstants;
import com.yjb.goods.util.Result;
import com.yjb.goods.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;
@RestController
@RepeatSubmit
@CrossOrigin
@Slf4j(topic = "recommend")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @CrossOrigin
    @PostMapping("login")
    public Result login(@RequestBody User userl){
        String username = userl.getUsername();
        String password = userl.getPassword();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //获取当前时间
        String currentTime = DateUtil.getCurrentTime();
        //判断缓存中是否存在用户
        User u = (User)redisTemplate.opsForValue().get(username);
        //如果为首次登录
        if ( u == null){
            User userByPasswordAndUser = userService.findUserByPasswordAndUser(new User(username, password));
            if (userByPasswordAndUser != null){
                //存入缓存有效期为5分钟
                redisTemplate.opsForValue().setIfAbsent(username,userByPasswordAndUser,3000, TimeUnit.SECONDS);
                //返回结果
                Result<User> userResult = Result.bulidResult(Status.OK, userByPasswordAndUser);

                //登陆成功，打印日志
                log.info("The user logining successful {}. Time {}.",userByPasswordAndUser,currentTime);
                return userResult;
            }
            User user = new User(username, password);
            Result<Object> errorResult = Result.bulidResult(Status.PWD_ERROR,user);
            //密码错误，打印日志
            log.info("Incorrect account name or password input {}. Time {}.",user,currentTime);
            return errorResult;
        }
        Result<Object> exitResult = Result.bulidResult(Status.EXIT,u);
        //用户已经登陆日志
        log.info("The user {}. has logged in the log Time {}.",u,currentTime);
        return exitResult;
    }

    @PostMapping("registry")
    @CrossOrigin
    public Result registry(@RequestBody User user){
        User userByName = userService.findUserByName(user);
        //获取当前时间
        String currentTime = DateUtil.getCurrentTime();
        if (userByName == null){
            User user1 = userService.addOrUpdateUser(user);
            Result<User> userResult = Result.bulidResult(Status.OK, user1);
            log.info("Successfully registered user {}. Time {}.",user1,currentTime);
            return  userResult;
        }
        Result<User> exitResult = Result.bulidResult(Status.EXIT,user);
        log.info("Successfully registered user {}. Time {}.",user,currentTime);
        return exitResult;
    }

    @GetMapping("lgout/{username}")
    @IsLogin
    public Result loginOut(@PathVariable("username") String username){
        redisTemplate.delete(username);
        Result<User> exitResult = Result.bulidResult(Status.OK,username);
        log.info("Successfully loginout user {}. Time {}.",username);
        return  exitResult;

    }
  }

