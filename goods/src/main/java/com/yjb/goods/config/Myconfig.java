package com.yjb.goods.config;

import com.yjb.goods.interceptor.LoginIntercepror;
import com.yjb.goods.interceptor.RepeatSubmitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Myconfig implements WebMvcConfigurer {
    @Autowired
    private RepeatSubmitInterceptor repeatSubmitInterceptor;
    @Autowired
    private LoginIntercepror loginIntercepror;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不进行拦截的url

        final String[] commonExclude = {"/error","/files/**"};
        registry.addInterceptor(loginIntercepror).excludePathPatterns(commonExclude);
        registry.addInterceptor(repeatSubmitInterceptor)
                .excludePathPatterns(commonExclude);


    }
}
