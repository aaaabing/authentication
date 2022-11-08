package com.lzr.bean;

import com.lzr.enums.Configure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取一些全局配置
 * @author lzr
 * @date 2022/11/8 15:12
 */
@Component
public class ConfigureBean {

    Map<String,String> configureMap;

    @Autowired
    Environment environment;

    @PostConstruct
    public void init(){
        configureMap = new HashMap<>();
        Field[] fields = Configure.class.getFields();
        for (Field field : fields) {
            try {
                String key = field.getName();
                String value = environment.getProperty((String) field.get(null));
                configureMap.put(key,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public String getConfigure(String key){
        return configureMap.get(key);
    }
}
