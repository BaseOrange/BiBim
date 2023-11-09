package com.ljc.system.service.system;

import com.ljc.model.pojo.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljc.model.vo.RoleMenuVo;

import java.util.List;

/**
 * 角色权限服务类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 根据RoleID获取MenuId列表
     *
     * @param roleId
     * @return
     */
    List<RoleMenu> getMenuIdsByRoleId(Long roleId);

    /**
     * 获取权限列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    List<RoleMenu> getRoleMenuList(Long current, Long size);

    /**
     * 根据角色id获取获取权限信息
     *
     * @param roleId 角色id
     * @return
     */
    List<RoleMenu> getRoleMenuListByRoleId(String roleId);

    /**
     * 修改权限信息
     *
     * @param roleMenuVo
     * @return
     */
    boolean updateRoleMenu(RoleMenuVo roleMenuVo);
}
