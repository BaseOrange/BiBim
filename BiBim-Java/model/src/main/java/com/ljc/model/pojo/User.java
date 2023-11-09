package com.ljc.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author dachengzi
 * @date 2022-12-24 09:49
 */
@Getter
@Setter
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField(exist = false)
    private String[] role;
    @TableField("email")
    private String email;
    @TableField("telnumber")
    private String telnumber;
    @TableField("status")
    private Integer status;
    @TableField("remark")
    private String remark;
    @TableField("create_time")
    private Timestamp createTime;
    @TableField("update_time")
    private Timestamp updateTime;
    @TableField("is_delete")
    @TableLogic  //逻辑删除 默认效果 0 没有删除 1 已经删除
    private Integer isDelete;
    @TableField("create_by")
    private Long createBy;
    @TableField("update_by")
    private Long updateBy;
}
