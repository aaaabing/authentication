package com.lzr.utils;

import com.lzr.annotation.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzr
 * @date 2022/9/28 11:21
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    WebApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 扫描url
         */
        RequestMappingHandlerMapping request = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = request.getHandlerMethods();
        //保存url和类
        Map<String,String> urlMap = new HashMap<>();
        map.forEach((k,v)->{
            if(v.getMethod().isAnnotationPresent(Authority.class)){
                //获取url
                String url = k.getPatternsCondition().getPatterns().toString();
                //获取权限
                String role =  v.getMethod().getAnnotation(Authority.class).role();
                urlMap.put(role,url);
            }
        });
        //@Todo 存入数据库
        System.out.println(urlMap);
    }
}
