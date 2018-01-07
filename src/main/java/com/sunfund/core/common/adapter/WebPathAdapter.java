package com.sunfund.core.common.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by jdy on 2017/10/10.
 * 添加请求拦截器,相当于xml中的拦截器适配
 */
@Configuration
public class WebPathAdapter extends WebMvcConfigurerAdapter {

    // 多个拦截器组成一个拦截器链
    // addPathPatterns 用于添加拦截规则
    // excludePathPatterns 用户排除拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new PermissonInterceptor()).addPathPatterns("/user/**");
        super.addInterceptors(registry);
    }

}
