package com.ljc.system.service.threeDimensional;

import com.ljc.model.pojo.Device;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * 设备服务类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
public interface DeviceService extends IService<Device> {

    /**
     * 获取模型信息列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    HashMap<String, Object> getModelList(Long current, Long size);

    /**
     * 删除模型
     *
     * @param deviceId 设备id
     * @return
     */
    boolean deleteModel(Long deviceId);

    /**
     * 批量删除模型
     *
     * @param deviceIds 设备id数组
     * @return
     */
    boolean deleteModels(String[] deviceIds);

    /**
     * 获取上传模型链接
     *
     * @param extension 文件扩展名
     * @return
     */
    String getUploadModelUrl(String extension);

    /**
     * 添加模型
     *
     * @param device 模型实体类
     * @return
     */
    boolean addModel(Device device);
}
