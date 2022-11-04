//package com.lzr.config;
//
//import com.lzr.filter.SecurityAuthenticationTokenFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// * 管理实例的bean
// * @author lzr
// */
//@Component
//public class BeanConfig {
//
//    @Bean
//    public FilterRegistrationBean registration(SecurityAuthenticationTokenFilter filter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
//        registration.setEnabled(false);
//        return registration;
//    }
//
//    /**
//     * 使用BCrypt进行加密
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
