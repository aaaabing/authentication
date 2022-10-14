package com.lzr.utils;

import com.alibaba.fastjson.JSONObject;
import com.lzr.annotation.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lzr
 * @date 2022/9/23 11:29
 */

@RestController
public class Controller {
    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public String test(@RequestBody String data){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        //验证用户信息
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "123";
    }
    @Authority(role = "user")
    @PostMapping("/test")
    public String test(){
        return "123";
    }
}
