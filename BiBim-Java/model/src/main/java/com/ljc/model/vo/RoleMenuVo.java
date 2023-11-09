package com.ljc.model.vo;

import lombok.Data;

/**
 * @author dachengzi
 * @Date 2023/2/9 21:07
 */
@Data
public class RoleMenuVo {
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 权限id数组
     */
    private String[] menuIds;
}
