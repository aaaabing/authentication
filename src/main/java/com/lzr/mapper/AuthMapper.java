package com.lzr.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author lzr
 * @date 2022/10/13 10:41
 */

@Mapper
public interface AuthMapper {
    /**
     * 获取所有角色
     * @param  url 路径
     * @return roles
     */
    @Select("select role from url_role where url = #{url} ")
    String getAllRoles(String url);

    /**
     * 添加权限
     * @param url 路径
     * @param role 角色
     */
    @Insert("insert into url_role(url,role) values(#{url},#{role})")
    int addUrlRole(@Param("url")String url,@Param("role")String role);

    /**
     * 检查表
     * @param tableSchema 库名
     * @param tableName 表名
     * @return 是否存在
     */
    @Select("  SELECT COUNT(1) FROM information_schema.tables WHERE\n" +
            "        table_schema=#{tableSchema} AND table_name = #{tableName}")
    int isExist(@Param("tableSchema") String tableSchema,@Param("tableName")String tableName);

    /**
     * 创建表
     */
    @Select("create table url_role( `url` varchar(150) NOT NULL,\n" +
            "  `role` varchar(150) NOT NULL,\n" +
            "  PRIMARY KEY (`url`, `role`))")
    void createTable();

    /**
     * 清空表
     */
    @Delete("delete from url_role")
    void clearTable();
}
