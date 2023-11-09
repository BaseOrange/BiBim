package com.ljc.system.controller.system;


import com.ljc.common.result.Result;
import com.ljc.model.enums.BusinessType;
import com.ljc.model.pojo.Role;
import com.ljc.model.pojo.RoleMenu;
import com.ljc.system.annotation.Log;
import com.ljc.system.service.system.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色控制器
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/system/role")
@Slf4j
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 获取角色列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @ApiOperation("获取角色列表")
    @GetMapping("/getRoleList/{current}/{size}")
    @PreAuthorize("hasAnyAuthority('ROOT')")
    @Log(title = "角色管理", businessType = BusinessType.SELECT)
    public Result getRoleList(@PathVariable("current") Long current, @PathVariable("size") Long size) {
        log.info("【获取角色列表请求】current：{}，size：{}", current, size);
        List<Role> roleList = roleService.getRoleList(current, size);
        log.info("【获取角色列表响应】roleList：{}", roleList);
        return Result.ok(roleList);
    }

    /**
     * 获取角色信息
     *
     * @param roleId 角色id
     * @return
     */
    @ApiOperation("获取角色信息")
    @GetMapping("/getRoleInfo/{roleId}")
    @PreAuthorize("hasAnyAuthority('ROOT')")
    @Log(title = "角色管理", businessType = BusinessType.SELECT)
    public Result getRoleInfo(@PathVariable("roleId") String roleId) {
        log.info("【获取角色信息请求】roleId：{}", roleId);
        Role roleInfo = roleService.getRoleInfo(roleId);
        log.info("【获取角色信息响应】roleInfo：{}", roleInfo);
        return Result.ok(roleInfo);
    }

    /**
     * 添加角色信息
     *
     * @param role 角色实体类
     * @return
     */
    @ApiOperation("添加角色信息")
    @PostMapping("/addRole")
    @PreAuthorize("hasAnyAuthority('ROOT')")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    public Result addRole(Role role) {
        log.info("【添加角色信息请求】role：{}", role);
        boolean isSuccess = roleService.addRole(role);
        log.info("【添加角色信息响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("添加成功");
        } else {
            return Result.fail("添加失败");
        }
    }

    /**
     * 修改角色信息
     *
     * @param role 角色实体类
     * @return
     */
    @ApiOperation("修改角色信息")
    @PostMapping("/updateRole")
    @PreAuthorize("hasAnyAuthority('ROOT')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    public Result updateRole(Role role) {
        log.info("【修改角色信息请求】role：{}", role);
        boolean isSuccess = roleService.updateRole(role);
        log.info("【修改角色信息响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("修改成功");
        } else {
            return Result.fail("修改失败");
        }
    }

    /**
     * 批量删除角色
     *
     * @param roleIdStr 设备id数组
     * @return
     */
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除角色")
    @DeleteMapping("/deleteRoles")
    @PreAuthorize("hasAnyAuthority('ROOT')")
    public Result deleteRoles(String roleIdStr) {
        log.info("【批量删除角色请求】roleIdStr：{}", roleIdStr);
        String[] roleIds = roleIdStr.split(",");
        boolean isSuccess = roleService.deleteRoles(roleIds);
        log.info("【批量删除角色响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }
}
