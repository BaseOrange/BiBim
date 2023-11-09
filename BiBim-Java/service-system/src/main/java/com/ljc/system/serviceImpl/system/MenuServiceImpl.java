package com.ljc.system.serviceImpl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljc.model.pojo.Menu;
import com.ljc.model.pojo.RoleMenu;
import com.ljc.system.mapper.MenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljc.system.service.system.MenuService;
import com.ljc.system.service.system.RoleMenuService;
import com.ljc.system.service.system.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限服务实现类
 *
 * @author BigOrange
 * @since 2023-01-13
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据用户id获取菜单列表
     *
     * @param userid
     * @return
     */
    @Override
    public List<Menu> getMenusByUserId(String userid) {
        // 查出用户的roleid
        Long roleId = userRoleService.getRoleIdByUserId(userid);

        // 查出菜单id列表
        List<RoleMenu> menuIdList = roleMenuService.getMenuIdsByRoleId(roleId);

        // 查询菜单列表
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        for (RoleMenu roleMenu : menuIdList) {
            menuQueryWrapper.eq("id", roleMenu.getMenuId()).or();
        }
        List<Menu> menus = menuMapper.selectList(menuQueryWrapper);

        // 对菜单列表进行处理,获取父菜单
        List<Menu> fatherList = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getParentId() == 0) {
                fatherList.add(menu);
            }
        }
        // 获取子菜单
        for (Menu menu : menus) {
            if (menu.getParentId() != 0L) {
                for (Menu father : fatherList) {
                    if (father.getId().equals(menu.getParentId())) {
                        father.getSubmenu().add(menu);
                    }
                }
            }
        }
        System.out.println(fatherList);
        return fatherList;
    }

    /**
     * 获取菜单信息
     *
     * @param menuId 菜单id
     * @return
     */
    @Override
    public Menu getMenuInfo(String menuId) {
        Menu menu = menuMapper.selectById(menuId);
        return menu;
    }

    /**
     * 修改菜单信息
     *
     * @param menu 菜单实体类
     * @return
     */
    @Override
    public boolean updateMenu(Menu menu) {
        int isNum = menuMapper.updateById(menu);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("重置密码失败");
            isSuccess = false;
        }
        return isSuccess;
    }
}
