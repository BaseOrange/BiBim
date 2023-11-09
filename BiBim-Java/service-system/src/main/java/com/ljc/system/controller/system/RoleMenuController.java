package com.ljc.system.controller.system;


import com.ljc.common.result.Result;
import com.ljc.model.enums.BusinessType;
import com.ljc.model.pojo.RoleMenu;
import com.ljc.model.pojo.User;
import com.ljc.model.vo.RoleMenuVo;
import com.ljc.system.annotation.Log;
import com.ljc.system.service.system.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色权限控制器
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Api(tags = "权限接口")
@RestController
@RequestMapping("/system/roleMenu")
@Slf4j
public class RoleMenuController {
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 获取权限列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @ApiOperation("获取权限列表")
    @GetMapping("/getRoleMenuList/{current}/{size}")
    @PreAuthorize("hasAnyAuthority('ROOT')")
    @Log(title = "权限管理", businessType = BusinessType.SELECT)
    public Result getRoleMenuList(@PathVariable("current") Long current, @PathVariable("size") Long size) {
        log.info("【获取权限列表请求】current：{}，size：{}", current, size);
        List<RoleMenu> roleMenuList = roleMenuService.getRoleMenuList(current, size);
        log.info("【获取权限列表响应】roleMenuList：{}", roleMenuList);
        return Result.ok(roleMenuList);
    }

    /**
     * 根据角色id获取获取权限信息
     *
     * @param roleId 角色id
     * @return
     */
    @ApiOperation("获取权限列表")
    @GetMapping("/getRoleMenuListByRoleId/{roleId}")
    @PreAuthorize("hasAnyAuthority('ROOT')")
    @Log(title = "权限管理", businessType = BusinessType.SELECT)
    public Result getRoleMenuListByRoleId(@PathVariable("roleId") String roleId) {
        log.info("【获取权限列表请求】roleId：{}", roleId);
        List<RoleMenu> roleMenuInfo = roleMenuService.getRoleMenuListByRoleId(roleId);
        log.info("【获取权限列表响应】roleMenuInfo：{}", roleMenuInfo);
        return Result.ok(roleMenuInfo);
    }

    /**
     * 修改权限信息
     *
     * @param roleMenuVo
     * @return
     */
    @ApiOperation("修改权限信息")
    @PutMapping("/updateRoleMenu/")
    @PreAuthorize("hasAnyAuthority('ROOT')")
    @Log(title = "权限管理", businessType = BusinessType.UPDATE)
    public Result updateRoleMenu(@RequestBody RoleMenuVo roleMenuVo) {
        log.info("【修改权限信息请求】roleMenuVo：{}", roleMenuVo);
        boolean isSuccess = roleMenuService.updateRoleMenu(roleMenuVo);
        log.info("【修改权限信息响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("修改成功");
        } else {
            return Result.fail("修改失败");
        }
    }
}
