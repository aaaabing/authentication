package com.lzr.factory;

import com.lzr.mapper.AuthMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lzr
 * @date 2022/11/7 14:38
 */
@Component
public class MybatisSqlSession {

    @Value("${security.mysql.driver}")
    private String driver;

    @Value("${security.mysql.username}")
    private String username;

    @Value("${security.mysql.password}")
    private String password;

    @Value("${security.mysql.url}")
    private String url;

    public SqlSession getSession(){
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("dev", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        // 注册指定映射器
        configuration.addMapper(AuthMapper.class);
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) new SqlSessionFactoryBuilder().build(configuration);
        //不加事务增删改查事务不会自动提交
        return sqlSessionFactory.openSession(true);
    }
}
