package com.lzr.controller;

import com.lzr.domain.AuthParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzr
 * @date 2022/9/23 11:29
 */

@Slf4j
@RestController
public class AdminController {
    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public String test(@RequestBody AuthParam authParam){
        log.info("123");

        //验证用户信息
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authParam.getUsername(), authParam.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "123";
    }
}
