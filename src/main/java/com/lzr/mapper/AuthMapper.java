package com.lzr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author lzr
 * @date 2022/10/13 10:41
 */

@Mapper
@Repository
public interface AuthMapper {
    /**
     * 获取所有角色
     * @return roles
     */
    List<String> getAllRoles();

    /**
     * 添加权限
     * @param url 路径
     * @param role 角色
     */
    void addUrlRole(@Param("url")String url,@Param("role")String role);
}
