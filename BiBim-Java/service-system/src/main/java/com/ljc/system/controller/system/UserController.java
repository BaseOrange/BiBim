package com.ljc.system.controller.system;


import com.ljc.common.result.Result;
import com.ljc.common.utils.JwtHelper;
import com.ljc.model.enums.BusinessType;
import com.ljc.model.pojo.Scene;
import com.ljc.model.pojo.User;
import com.ljc.system.annotation.Log;
import com.ljc.system.mapper.UserMapper;
import com.ljc.system.service.system.RoleService;
import com.ljc.system.service.system.UserRoleService;
import com.ljc.system.service.system.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 用户控制器
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/system/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 登录后查询用户信息
     *
     * @param request
     * @return
     */
    @ApiOperation("登录后查询用户信息")
    @GetMapping("/info")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    @Log(title = "用户管理", businessType = BusinessType.SELECT)
    public Result info(HttpServletRequest request) {
        // 获取请求头的token
        String token = request.getHeader("Authorization");

        // 从token中获取用户名称
        String username = JwtHelper.getUsername(token);

        // 根据用户名称获取用户信息(基本信息，按钮权限)
        User userInfo = userService.getUserInfoByUserName(username);
        userInfo.setPassword("");

        // 获取角色信息
        Long roleId = userRoleService.getRoleIdByUserId(userInfo.getId().toString());
        String[] role = {roleService.getRoleInfo(roleId.toString()).getRoleCode()};
        userInfo.setRole(role);

        return Result.ok(userInfo);
    }

    /**
     * 注册用户
     *
     * @param user 用户实体类
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('ROOT')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    public Result registerUser(@RequestBody User user) {
        log.info("【用户注册请求】user：{}", user);
        boolean isSuccess = userService.registerUser(user);
        log.info("【用户注册响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("注册成功");
        } else {
            return Result.fail("注册失败");
        }
    }

    /**
     * 获取用户列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @ApiOperation("获取用户列表")
    @GetMapping("/getUserList/{current}/{size}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
    @Log(title = "用户管理", businessType = BusinessType.SELECT)
    public Result getUserList(@PathVariable("current") Long current, @PathVariable("size") Long size) {
        log.info("【获取用户列表请求】");
        HashMap<String, Object> userMap = userService.getUserMap(current, size);
        log.info("【获取用户列表响应】userList：{}", userMap);
        return Result.ok(userMap);
    }

    /**
     * 修改用户
     *
     * @param user 用户实体类
     * @return
     */
    @ApiOperation("修改用户")
    @PutMapping("/updateUser")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    public Result updateUser(@RequestBody User user) {
        log.info("【修改用户请求】User:{}", user);
        boolean isSuccess = userService.updateUser(user);
        log.info("【修改用户响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("修改成功");
        } else {
            return Result.fail("修改失败");
        }
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return
     */
    @ApiOperation("删除用户")
    @DeleteMapping("/deleteUser/{userId}")
    @PreAuthorize("hasAnyAuthority('ROOT')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    public Result deleteUser(@PathVariable("userId") String userId) {
        log.info("【删除用户请求】userId:{}", userId);
        boolean isSuccess = userService.deleteUser(userId);
        log.info("【删除用户响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 重置用户密码
     *
     * @param userId 用户id
     * @return
     */
    @ApiOperation("重置密码")
    @PutMapping("/resetUser/{userId}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    public Result resetUser(@PathVariable("userId") String userId) {
        log.info("【重置密码请求】userId:{}", userId);
        boolean isSuccess = userService.resetUser(userId);
        log.info("【重置密码响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("重置成功");
        } else {
            return Result.fail("重置失败");
        }
    }

}
