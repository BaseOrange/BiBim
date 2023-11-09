package com.ljc.system.serviceImpl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljc.model.pojo.LoginLog;
import com.ljc.model.pojo.Role;
import com.ljc.model.pojo.UserRole;
import com.ljc.system.mapper.RoleMapper;
import com.ljc.system.mapper.UserRoleMapper;
import com.ljc.system.service.system.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 角色服务实现类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


    /**
     * 获取角色列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @Override
    public List<Role> getRoleList(Long current, Long size) {
        Page<Role> rolePage = new Page<>(current, size);
        Page<Role> roleList = roleMapper.selectPage(rolePage, null);
        return roleList.getRecords();
    }

    /**
     * 获取角色信息
     *
     * @param roleId 角色id
     * @return
     */
    @Override
    public Role getRoleInfo(String roleId) {
        Role role = roleMapper.selectById(roleId);
        return role;
    }

    /**
     * 添加角色信息
     *
     * @param role 角色实体类
     * @return
     */
    @Override
    public boolean addRole(Role role) {
        int isNum = roleMapper.insert(role);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 修改角色信息
     *
     * @param role 角色实体类
     * @return
     */
    @Override
    public boolean updateRole(Role role) {
        int isNum = roleMapper.updateById(role);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 批量删除角色
     *
     * @param roleIds 设备id数组
     * @return
     */
    @Override
    public boolean deleteRoles(String[] roleIds) {
        int isNum = roleMapper.deleteBatchIds(Arrays.asList(roleIds));
        boolean isSuccess;
        if (isNum == roleIds.length) {
            isSuccess = true;
        } else {
            log.error("批量删除角色失败");
            isSuccess = false;
        }
        return isSuccess;
    }
}
