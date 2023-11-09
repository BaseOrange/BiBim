package com.ljc.system.service.system;

import com.ljc.model.pojo.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户角色服务类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 通过用户id获取roleId
     *
     * @param userId 用户id
     * @return
     */
    Long getRoleIdByUserId(String userId);

    /**
     * 通过id获取用户角色集合
     *
     * @param id
     * @return
     */
    List<String> getUserRoleList(Long id);
}
