package com.ljc.system.serviceImpl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljc.model.pojo.Role;
import com.ljc.model.pojo.RoleMenu;
import com.ljc.model.pojo.Scene;
import com.ljc.model.vo.RoleMenuVo;
import com.ljc.system.mapper.RoleMenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljc.system.service.system.RoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限角色服务实现类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Service
@Slf4j
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 根据RoleID获取MenuId列表
     *
     * @param roleId
     * @return
     */
    @Override
    public List<RoleMenu> getMenuIdsByRoleId(Long roleId) {
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id", roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuQueryWrapper);
        return roleMenus;
    }

    /**
     * 获取权限列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @Override
    public List<RoleMenu> getRoleMenuList(Long current, Long size) {
        Page<RoleMenu> roleMenuPage = new Page<>(current, size);
        Page<RoleMenu> roleMenuList = roleMenuMapper.selectPage(roleMenuPage, null);
        return roleMenuList.getRecords();
    }

    /**
     * 根据角色id获取获取权限信息
     *
     * @param roleId 角色id
     * @return
     */
    @Override
    public List<RoleMenu> getRoleMenuListByRoleId(String roleId) {
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id", roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuQueryWrapper);
        return roleMenus;
    }

    /**
     * 修改权限信息
     *
     * @param roleMenuVo
     * @return
     */
    @Override
    public boolean updateRoleMenu(RoleMenuVo roleMenuVo) {
        Long roleId = Long.parseLong(roleMenuVo.getRoleId());

        // 删除原有的信息数据
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id", roleId);
        roleMenuMapper.delete(roleMenuQueryWrapper);

        // 添加新的数据
        ArrayList<RoleMenu> roleMenus = new ArrayList<>();
        for (String menuId : roleMenuVo.getMenuIds()) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setId(null);
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(Long.parseLong(menuId));
            roleMenus.add(roleMenu);
        }

        boolean isSuccess = saveBatch(roleMenus);
        log.info("添加权限信息状态:{}", isSuccess);
        return isSuccess;
    }
}
