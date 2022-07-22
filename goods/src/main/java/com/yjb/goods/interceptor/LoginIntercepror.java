package com.yjb.goods.interceptor;

import com.yjb.goods.annotation.IsLogin;
import com.yjb.goods.annotation.RepeatSubmit;
import com.yjb.goods.exception.NoLoginException;
import com.yjb.goods.util.GlobalSessionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/*
* 拦截处登陆之外的所有请求，优先级高于重复提交
* */
@Component
public class LoginIntercepror implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            String url = request.getRequestURL().toString();

            //只拦截标注@IsLogin的类或者方法
            HandlerMethod method = (HandlerMethod) handler;
            IsLogin isLoginByMethod = AnnotationUtils.findAnnotation(method.getMethod(), IsLogin.class);
            IsLogin isLoginByCls = AnnotationUtils.findAnnotation(method.getMethod().getDeclaringClass(), IsLogin.class);


            //没有标注的类，直接返回true，放弃拦截
            if (Objects.isNull(isLoginByMethod) && Objects.isNull(isLoginByCls)) return true;
            String username = request.getParameter("username");
            if(username == null)  throw new NoLoginException(url);
            //使用redis存储当前浏览器会话信息
            if (redisTemplate.opsForValue().get(username) != null) {
                return true;
            } else {
                throw new NoLoginException(url);
            }

        }
        return true;
    }
}




