package com.ljc.system.service.threeDimensional;

import com.ljc.model.pojo.DeviceDeploy;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljc.model.vo.DeviceInfoVo;

import java.util.List;

/**
 * 设备部署服务类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
public interface DeviceDeployService extends IService<DeviceDeploy> {

    /**
     * 获取场景设备部署信息
     *
     * @param sceneId 场景id
     * @return
     */
    List<DeviceDeploy> getSceneDeviceDeployInfo(Long sceneId);

    /**
     * 更新部署设备信息
     *
     * @param deviceDeployList 部署设备列表
     * @return
     */
    boolean updataDeviceDeployInfo(List<DeviceDeploy> deviceDeployList);

    /**
     * 删除部署设备
     *
     * @param deviceDeployId 部署设备id
     * @return
     */
    boolean deleteDeviceDeploy(Long deviceDeployId);

    /**
     * 更新设备状态信息
     *
     * @param deviceInfoVo 设备状态信息VO
     * @return
     */
    boolean updataDeviceInfo(DeviceInfoVo deviceInfoVo);
}
