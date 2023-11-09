package com.ljc.system.mapper;

import com.ljc.model.pojo.DeviceDeploy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljc.model.vo.DeviceInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备部署 Mapper 接口
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Mapper
public interface DeviceDeployMapper extends BaseMapper<DeviceDeploy> {

    /**
     * 获取场景设备部署信息
     *
     * @param sceneId 场景id
     * @return
     */
    List<DeviceDeploy> getSceneDeviceDeployInfo(@Param("sceneId") Long sceneId);

    /**
     * 更新设备状态信息
     *
     * @param deviceInfoVo 设备状态信息VO
     * @return
     */
    int updateDeviceInfo(DeviceInfoVo deviceInfoVo);
}
