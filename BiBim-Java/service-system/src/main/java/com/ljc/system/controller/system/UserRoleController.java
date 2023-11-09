package com.ljc.system.controller.system;


import com.ljc.system.service.system.UserRoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 用户角色控制器
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Api(tags = "用户角色接口")
@RestController
@RequestMapping("/system/userRole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;


}
