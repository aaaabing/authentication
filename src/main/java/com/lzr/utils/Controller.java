package com.lzr.utils;

import com.lzr.annotation.Authority;
import com.lzr.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lzr
 * @date 2022/9/23 11:29
 */

@RestController
public class Controller {

    @Autowired
    FileService fileServiceInterface;

    @Authority()
    @PostMapping("/test")
    public String test(HttpServletRequest httpServletRequest){
        return httpServletRequest.getSession().getId();
    }
    //    @PostMapping("/test")
//    public String test(@RequestParam("file") MultipartFile multipartFile){
//        fileServiceInterface.download("courgette_1663919605614.log");
//        return "123";
//    }
}
