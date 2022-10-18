package com.lzr.utils;


import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * @author lzr
 * @date 2022/10/13 9:48
 */
@Component
public class InitContainer {

    @PostConstruct
    public void init() {
        //检测表是否存在 @Todo 完成初始化工作
    }
}