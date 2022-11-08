package com.lzr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
    @Select("select role from tb_user where url = #{url} ")
    String getAllRoles(String url);

    /**
     * 添加权限
     * @param url 路径
     * @param role 角色
     */
//    void addUrlRole(@Param("url")String url,@Param("role")String role);
}
