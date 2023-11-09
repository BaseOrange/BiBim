package com.ljc.model.vo;

import lombok.Data;

/**
 * 设备状态信息VO
 *
 * @author dachengzi
 * @date 2023-01-08 21:57
 */
@Data
public class DeviceInfoVo {

    /**
     * 设备id
     */
    private String id;

    /**
     * 设备信息
     */
    private String deviceInfo;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户id
     */
    private String userId;

}

