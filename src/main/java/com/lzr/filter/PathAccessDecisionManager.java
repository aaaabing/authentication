package com.lzr.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import java.util.Collection;

import static com.lzr.enums.SecurityCode.REFUSE;
import static com.lzr.enums.SecurityCode.WHITE_REQUEST;

/**
 * @author lzr
 * @date 2022/10/14 10:40
 */
@Slf4j
@Component
public class PathAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            if(REFUSE.equalsIgnoreCase(configAttribute.getAttribute())){
                if(authentication instanceof AnonymousAuthenticationToken){
                    log.warn("资源不存在");
                    throw new AccessDeniedException("资源不存在");
                }else{
                    log.warn("权限不足1");
                    throw new AccessDeniedException("权限不足");
                }
            }
            //白名单放行
            if (WHITE_REQUEST.equalsIgnoreCase(configAttribute.getAttribute())){
                return;
            }else{
                Collection<? extends GrantedAuthority> c = authentication.getAuthorities();
                //遍历当前登录用户的角色，如果具有权限则放行 暂将一个人对应一个角色
                for (GrantedAuthority grantedAuthority : c) {
                    if(grantedAuthority.getAuthority().equalsIgnoreCase(configAttribute.getAttribute())){
                        return;
                    }
                }
                //遍历完没有返回的话，说明没有权限
                log.warn("权限不足");
                throw new AccessDeniedException("权限不足");
            }
        }
        if (authentication instanceof AnonymousAuthenticationToken){
            throw new AccessDeniedException("用户未登录");
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
