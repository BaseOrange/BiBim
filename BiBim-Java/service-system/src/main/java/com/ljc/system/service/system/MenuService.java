package com.ljc.system.service.system;

import com.ljc.model.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单服务类
 *
 * @author BigOrange
 * @since 2023-01-13
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据用户id获取菜单列表
     *
     * @param userid
     * @return
     */
    List<Menu> getMenusByUserId(String userid);

    /**
     * 获取菜单信息
     *
     * @param menuId 菜单id
     * @return
     */
    Menu getMenuInfo(String menuId);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单实体类
     * @return
     */
    boolean updateMenu(Menu menu);
}
