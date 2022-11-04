//package com.lzr.service;
//
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author lzr
// * @date 2021/9/8 19:36
// */
//@Service("userDetailsService")
//public class MyUserDetailsService implements UserDetailsService {
//
//
//    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("user"));
//        com.lzr.domain.User user= new com.lzr.domain.User();
//        user.setPassWord("12");
//        user.setUserName("12");
//        UserDetails userDetails = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()),authorities);
//        return userDetails;
//    }
//}
