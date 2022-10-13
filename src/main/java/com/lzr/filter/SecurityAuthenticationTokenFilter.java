package com.lzr.filter;

import com.lzr.util.IpUtil;
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
@Component
public class SecurityAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取session
        String session =  httpServletRequest.getSession().getId();
        //获取ip
        String ipAddress = IpUtil.getIpAddr(httpServletRequest);
        //去缓存中查找用户是否登录或者登录是否过期
        //@TODO
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
