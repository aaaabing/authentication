//package com.lzr.domain;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
///**
// * @author lzr
// * @date 2021/9/8 19:01
// */
//public class User implements UserDetails {
//    private String userName;
//    private String passWord;
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public void setPassWord(String passWord) {
//        this.passWord = passWord;
//    }
//
//    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
//        this.authorities = authorities;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.authorities;
//    }
//    @Override
//    public String getPassword() {
//        return this.passWord;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.userName;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "userName='" + userName + '\'' +
//                ", passWord='" + passWord + '\'' +
//                ", authorities=" + authorities +
//                '}';
//    }
//}
