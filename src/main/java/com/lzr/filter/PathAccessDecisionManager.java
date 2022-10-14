package com.lzr.filter;

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

/**
 * @author lzr
 * @date 2022/10/14 10:40
 */
@Component
public class PathAccessDecisionManager implements AccessDecisionManager {
    private final static String REFUSE_MANE = "REFUSE";
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        collection.forEach(e->{
            if(REFUSE_MANE.equalsIgnoreCase(e.getAttribute())){
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("资源不存在");
                }else{
                    throw new AccessDeniedException("权限不足");
                }
            }

        });
        if (authentication instanceof AnonymousAuthenticationToken){
            throw new AccessDeniedException("用户未登录");
        }
//        throw new AccessDeniedException("权限不足");
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
