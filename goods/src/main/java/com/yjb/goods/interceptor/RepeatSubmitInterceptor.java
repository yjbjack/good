package com.yjb.goods.interceptor;


import cn.hutool.core.annotation.AnnotationUtil;
import com.yjb.goods.annotation.RepeatSubmit;
import com.yjb.goods.exception.RepeatSubmitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/*
* 重复拦截2，拦截重复请求
* */
@Component
public class RepeatSubmitInterceptor implements HandlerInterceptor {

    //使用redis缓存用户url为5s
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod){
            //只拦截标注RepeatSubmit的类或者方法
            HandlerMethod method = (HandlerMethod) handler;
            RepeatSubmit repeatSubmitByMethod = AnnotationUtils.findAnnotation(method.getMethod(), RepeatSubmit.class);
            RepeatSubmit repeatSubmitByCls = AnnotationUtils.findAnnotation(method.getMethod().getDeclaringClass(), RepeatSubmit.class);

            //没有标注的类，直接返回true，放弃拦截
            if(Objects.isNull(repeatSubmitByMethod) && Objects.isNull(repeatSubmitByCls)) return true;

            //组合判断条件，这里只用了url
            String requestURL = request.getRequestURL().toString();

            //拿到用户信息,暂时不用token
            String username = request.getParameter("username");
            //判断redeis是否已经存在数据，
            Boolean ifAbsent = stringRedisTemplate.opsForValue().setIfAbsent(requestURL, "",
                    Objects.nonNull(repeatSubmitByMethod) ? repeatSubmitByMethod.seconds() : repeatSubmitByCls.seconds(), TimeUnit.SECONDS);

            //如果setIfAbsent为false，表示已经发送请求，直接抛出异常
            if (ifAbsent != null && !ifAbsent) throw  new RepeatSubmitException(username);

        }
        return true;

    }
}
