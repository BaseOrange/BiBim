package com.ljc.system.service.system;

import com.ljc.model.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色服务类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
public interface RoleService extends IService<Role> {


    /**
     * 获取角色列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    List<Role> getRoleList(Long current, Long size);

    /**
     * 获取角色信息
     *
     * @param roleId 角色id
     * @return
     */
    Role getRoleInfo(String roleId);

    /**
     * 添加角色信息
     *
     * @param role 角色实体类
     * @return
     */
    boolean addRole(Role role);

    /**
     * 修改角色信息
     *
     * @param role 角色实体类
     * @return
     */
    boolean updateRole(Role role);

    /**
     * 批量删除角色
     *
     * @param roleIds 设备id数组
     * @return
     */
    boolean deleteRoles(String[] roleIds);
}
