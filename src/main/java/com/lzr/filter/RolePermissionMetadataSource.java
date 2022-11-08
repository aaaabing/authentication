package com.lzr.filter;

import com.lzr.mapper.AuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import static com.lzr.enums.SecurityCode.REFUSE;
import static com.lzr.enums.SecurityCode.WHITE_REQUEST;

/**
 * @author lzr
 * @date 2022/10/14 9:39
 */
@Slf4j
@Component
public class RolePermissionMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    AuthMapper authMapper;

    @Value("${security.redis.enableRedis}")
    boolean enableRedis;
    List<String> whiteUrl = new LinkedList<>();
    @PostConstruct
    public void init(){
        whiteUrl.add("/login");
    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) o;
        //获取请求url
        String url = filterInvocation.getRequestUrl();
        log.info("请求url：{}",url);
        //白名单直接放行 @Todo 应用正则判断
        if (whiteUrl.contains(url)){
            log.info("白名单放行");
            return SecurityConfig.createList(WHITE_REQUEST);
        }else{
            String role = null;
            //开启redis
            if(enableRedis){
            }else{
                role = authMapper.getAllRoles(url);
                log.info("获取到权限为{}",role);
            }
            //去redis里获取url 对应路径
            if(role!=null&&!role.isEmpty()){
                String[] roles = role.split(",");
                List<ConfigAttribute> configAttributes = new LinkedList<>();
                for (String s : roles) {
                    configAttributes.add(new SecurityConfig(s));
            }
                return configAttributes;
            }
            log.info("无匹配权限");
            return SecurityConfig.createList(REFUSE);
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        List<String> roles = Arrays.asList("user");
//        List<ConfigAttribute> configAttributes = new LinkedList<>();
//        roles.forEach(e->{
//            configAttributes.add(new SecurityConfig(e));
//        });
//        return configAttributes;
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
