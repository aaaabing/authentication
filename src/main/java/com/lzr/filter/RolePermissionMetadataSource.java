package com.lzr.filter;

import com.lzr.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lzr
 * @date 2022/10/14 9:39
 */
@Component
public class RolePermissionMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AuthMapper authMapper;
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) o;
        //获取请求url
        String url = filterInvocation.getRequestUrl();
        //@Todo 用redis获取
//        String role = (String) redisTemplate.opsForValue().get(url);
        //@Todo 此处测试
        String role = "/login";
        //去redis里获取url 对应路径
        if(!role.isEmpty()){
            String[] roles = role.split(",");
            List<ConfigAttribute> configAttributes = new LinkedList<>();
            for (String s : roles) {
                configAttributes.add(new SecurityConfig(s));
            }
            return configAttributes;
        }
        return SecurityConfig.createList("REFUSE");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        List<String> roles = authMapper.getAllRoles();
        List<ConfigAttribute> configAttributes = new LinkedList<>();
        roles.forEach(e->{
            configAttributes.add(new SecurityConfig(e));
        });
        return configAttributes;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
