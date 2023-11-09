package com.ljc.system.serviceImpl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljc.model.pojo.Role;
import com.ljc.model.pojo.UserRole;
import com.ljc.system.mapper.RoleMapper;
import com.ljc.system.mapper.UserRoleMapper;
import com.ljc.system.service.system.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色服务实现类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 通过id获取用户角色集合
     *
     * @param id
     * @return
     */
    @Override
    public List<String> getUserRoleList(Long id) {
        // 根据id查出用户角色id列表
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", id);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);

        // 根据用户角色id列表查出角色编号
        List<Long> roleIds = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roleIds.add(userRole.getRoleId());
        }
        List<Role> roles = roleMapper.selectBatchIds(roleIds);

        // 遍历获取角色编码
        List<String> roleCodes = new ArrayList<>();
        for (Role role : roles) {
            roleCodes.add(role.getRoleCode());
        }

        return roleCodes;
    }

    /**
     * 通过用户id获取roleId
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Long getRoleIdByUserId(String userId) {
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", userId);
        UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);
        return userRole.getRoleId();
    }
}
