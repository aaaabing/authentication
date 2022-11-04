package com.lzr.factory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author lzr
 * @date 2022/11/4 16:14
 */

public class SqlFactory implements InitializingBean {

    @Value("${security.driver}")
    private String driver;

    @Value("${security.username}")
    private String username;

    @Value("${security.password}")
    private String password;

    @Value("${security.url}")
    private String url;

    private Connection connection;

    public Connection getInstance(){
        return this.connection;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            System.out.println(username+password+url);
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
