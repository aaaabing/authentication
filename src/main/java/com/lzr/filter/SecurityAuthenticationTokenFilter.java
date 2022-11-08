package com.lzr.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lzr
 * @date 2022/9/27 17:25
 */
@Slf4j
@Component
public class SecurityAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        //获取session
//        String session =  httpServletRequest.getSession().getId();
//        //获取ip
//        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
//        //去缓存中查找用户是否登录或者登录是否过期
        log.info("执行过滤");
        UserDetails userDetails = null;
        //token是根据userName加密的，根据token可以反解出userName
//        String username = httpServletRequest.getParameter("username");
        //根据用户名查找用户具体信息
        userDetails = userDetailsService.loadUserByUsername("12");
        //创建AuthenticationToken
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        if (authentication != null) {
            //将authentication放入上下文对象中管理
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
