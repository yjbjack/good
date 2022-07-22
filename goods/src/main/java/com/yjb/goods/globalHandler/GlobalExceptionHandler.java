package com.yjb.goods.globalHandler;

import com.yjb.goods.exception.NoLoginException;
import com.yjb.goods.exception.RepeatSubmitException;
import com.yjb.goods.util.DateUtil;
import com.yjb.goods.util.Result;
import com.yjb.goods.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //定义处理异常的方法
    @ExceptionHandler
    @ResponseBody
    public Result exceptionHandler(Exception e){
        String currentTime = DateUtil.getCurrentTime();

        if (e instanceof RepeatSubmitException){
            //频繁请求前获取当前登录用户信息
            RepeatSubmitException repeatSubmitException = (RepeatSubmitException) e;
            String usernameRepeat = repeatSubmitException.getMessage();
            log.error("User {}. repeated reques Time {}.",usernameRepeat,currentTime);
            Result<Object> repResult = Result.bulidResult(Status.REQ_REPEAT, usernameRepeat);
            return repResult;
        }
        if (e instanceof NoLoginException){
            NoLoginException loginException = (NoLoginException) e;
            String message = loginException.getMessage();
            log.error("The URL {}. is not logged in Time {}.",message,currentTime);
            Result<Object> repResult = Result.bulidResult(Status.REQ_REPEAT, message);
            return repResult;
        }
        if (e instanceof NullPointerException){
            NullPointerException nullPointerException = (NullPointerException) e;
            log.error("用户未登陆请求商品数据 Time {}.",currentTime);
            Result<Object> serviceResult = Result.bulidResult(Status.SERVICE_UNAVAILABLE, "用户未登陆请求商品数据");
            return serviceResult;
        }
        if (e instanceof Exception){
            e.printStackTrace();
        }
        return Result.bulidResult(Status.OK);
    }


}
