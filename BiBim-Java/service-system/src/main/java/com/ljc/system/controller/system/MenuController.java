package com.ljc.system.controller.system;


import com.ljc.common.result.Result;
import com.ljc.model.enums.BusinessType;
import com.ljc.model.pojo.Menu;
import com.ljc.system.annotation.Log;
import com.ljc.system.service.system.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限控制器
 *
 * @author BigOrange
 * @since 2023-01-13
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/system/menu")
@Slf4j
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表
     *
     * @return
     */
    @Log(title = "菜单管理", businessType = BusinessType.SELECT)
    @ApiOperation("获取菜单列表")
    @GetMapping("/getMenus")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    public Result getMenus() {
        // 获取用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        log.info("【获取菜单列表请求】userId：{}", userId);
        // 根据用户id获取菜单列表
        List<Menu> menus = menuService.getMenusByUserId(userId);
        log.info("【获取菜单列表响应】menus：{}", menus);
        return Result.ok(menus);
    }

    /**
     * 获取菜单信息
     *
     * @param menuId 菜单id
     * @return
     */
    @Log(title = "菜单管理", businessType = BusinessType.SELECT)
    @ApiOperation("获取菜单信息")
    @GetMapping("/getMenuInfo/{menuId}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
    public Result getMenuInfo(@PathVariable("menuId") String menuId) {
        log.info("【获取菜单信息请求】menuId：{}", menuId);
        Menu menu = menuService.getMenuInfo(menuId);
        log.info("【获取菜单信息响应】menu：{}", menu);
        return Result.ok(menu);
    }

    /**
     * 修改菜单信息
     *
     * @param menu 菜单实体类
     * @return
     */
    @Log(title = "菜单管理", businessType = BusinessType.SELECT)
    @ApiOperation("修改菜单信息")
    @PutMapping("/updateMenu/")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
    public Result updateMenu(@RequestBody Menu menu) {
        log.info("【修改菜单信息请求】menu：{}", menu);
        boolean isSuccess = menuService.updateMenu(menu);
        log.info("【修改菜单信息响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("修改成功");
        } else {
            return Result.fail("修改失败");
        }
    }

}
