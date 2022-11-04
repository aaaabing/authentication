package com.lzr.utils;

import com.lzr.annotation.Authority;
import com.lzr.factory.SqlFactory;
import com.lzr.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    @Autowired
    ApplicationContext applicationContextSpring;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 扫描url
         */
        SqlFactory sqlFactory = SpringUtils.getBean("SqlFactory");
        Connection connection = sqlFactory.getInstance();
        System.out.println(connection.equals(null));
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
        //数据库和redis 各自保存一份
        urlMap.forEach((k,v)->{
//            authMapper.addUrlRole(k.toString(),v.toString());
            String sql = "select * from tb_user";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println(preparedStatement.execute());
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            redisTemplate.opsForValue().set(v.toString().substring(1,v.toString().length()-1),k.toString());
            System.out.println((v.toString().substring(1,v.toString().length()-1)));
        });
    }
}
