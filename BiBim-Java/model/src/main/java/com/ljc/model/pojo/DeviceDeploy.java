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
 * 设备部署实体类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Getter
@Setter
@TableName("sys_device_deploy")
public class DeviceDeploy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 设备id
     */
    @TableField("device_id")
    private Long deviceId;

    /**
     * 场景id
     */
    @TableField("scene_id")
    private Long sceneId;

    /**
     * 位置x
     */
    @TableField("position_x")
    private BigDecimal positionX;

    /**
     * 位置y
     */
    @TableField("position_y")
    private BigDecimal positionY;

    /**
     * 位置z
     */
    @TableField("position_z")
    private BigDecimal positionZ;

    /**
     * 缩放比例x
     */
    @TableField("scaling_x")
    private BigDecimal scalingX;

    /**
     * 缩放比例y
     */
    @TableField("scaling_y")
    private BigDecimal scalingY;

    /**
     * 缩放比例z
     */
    @TableField("scaling_z")
    private BigDecimal scalingZ;

    /**
     * 旋转x
     */
    @TableField("rotation_x")
    private BigDecimal rotationX;

    /**
     * 旋转y
     */
    @TableField("rotation_y")
    private BigDecimal rotationY;

    /**
     * 旋转z
     */
    @TableField("rotation_z")
    private BigDecimal rotationZ;

    /**
     * json格式，描述设备运行参数
     */
    @TableField("device_info")
    private String deviceInfo;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

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
    //@TableLogic
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
