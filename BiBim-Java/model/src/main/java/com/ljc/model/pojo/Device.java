package com.ljc.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * 设备实体类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Getter
@Setter
@TableName("sys_device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建者id
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 设备名
     */
    @TableField("device_name")
    private String deviceName;

    /**
     * 设备尺寸 5,12,3 长宽高 米
     */
    @TableField("device_size")
    private String deviceSize;

    /**
     * 设备缩放比例x
     */
    @TableField("scaling_x")
    private BigDecimal scalingX;

    /**
     * 设备缩放比例y
     */
    @TableField("scaling_y")
    private BigDecimal scalingY;

    /**
     * 设备缩放比例z
     */
    @TableField("scaling_z")
    private BigDecimal scalingZ;

    /**
     * 模型存储连接
     */
    @TableField("device_url")
    private String deviceUrl;

    /**
     * 设备描述
     */
    @TableField("device_info")
    private String deviceInfo;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除（0:可用 1:已删除）
     */
    @TableField("is_delete")
    @TableLogic
    private Boolean isDelete;

    /**
     * 创建人
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private Long updateBy;


}
