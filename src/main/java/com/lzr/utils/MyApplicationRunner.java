package com.lzr.utils;

import com.lzr.annotation.Authority;
import com.lzr.bean.ConfigureBean;
import com.lzr.factory.MybatisSqlSession;
import com.lzr.mapper.AuthMapper;
import com.sun.deploy.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.util.HashMap;
import java.util.Map;
import static com.lzr.enums.SecurityCode.TABLE_EXIST;
import static com.lzr.enums.Configure.SCHEMA;

/**
 * @author lzr
 * @date 2022/9/28 11:21
 */
@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    WebApplicationContext applicationContext;

    @Autowired
    ApplicationContext applicationContextSpring;

    @Autowired
    MybatisSqlSession sqlSession;

    @Autowired
    ConfigureBean configureBean;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 扫描url
         */
        log.info("初始化");
        AuthMapper authMapper = sqlSession.getSession().getMapper(AuthMapper.class);
        //如果事先有库，则不创建
        if(TABLE_EXIST==authMapper.isExist(configureBean.getConfigure(SCHEMA),"url_table")){
            log.info("创建数据库");
            authMapper.createTable();
        }
        //清空数据
        authMapper.clearTable();
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
            log.info("插入role:{},url:{}",k.toString(),v.toString().substring(1,v.toString().length()-1));
            int res = authMapper.addUrlRole(k.toString(),v.toString().substring(1,v.toString().length()-1));
            log.info("成功插入{}条数据",res);
//            redisTemplate.opsForValue().set(v.toString().substring(1,v.toString().length()-1),k.toString());
        });
    }
}
