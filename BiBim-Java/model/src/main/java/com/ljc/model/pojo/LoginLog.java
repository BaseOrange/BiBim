package com.ljc.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * 登录日志表
 *
 * @author dachengzi
 * @since 2023-01-16
 */
@Getter
@Setter
@TableName("sys_login_log")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 登录地址
     */
    @TableField("ipaddr")
    private String ipaddr;

    /**
     * 登录状态（0成功，1失败）
     */
    @TableField("status")
    private Integer status;

    /**
     * 提示信息
     */
    @TableField("msg")
    private String msg;

    /**
     * 访问时间
     */
    @TableField("access_time")
    private LocalDateTime accessTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 删除标记（0可用，1删除）
     */
    @TableField("is_delete")
    @TableLogic
    private Integer isDelete;


}
