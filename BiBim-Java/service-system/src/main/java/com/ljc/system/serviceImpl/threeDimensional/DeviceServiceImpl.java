package com.ljc.system.serviceImpl.threeDimensional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljc.common.utils.MinioUtils;
import com.ljc.model.pojo.Device;
import com.ljc.model.pojo.Scene;
import com.ljc.system.mapper.DeviceMapper;
import com.ljc.system.service.threeDimensional.DeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 设备服务实现类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 获取模型信息列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @Override
    public HashMap<String, Object> getModelList(Long current, Long size) {
        Page<Device> devicePage = new Page<>(current, size);
        Page<Device> deviceList = deviceMapper.selectPage(devicePage, null);

        Integer count = deviceMapper.selectCount(null);
        HashMap<String, Object> deviceMap = new HashMap<>();
        deviceMap.put("count",count);
        deviceMap.put("list",devicePage.getRecords());
        return deviceMap;
    }

    /**
     * 删除模型
     *
     * @param deviceId 设备id
     * @return
     */
    @Override
    public boolean deleteModel(Long deviceId) {
        int isNum = deviceMapper.deleteById(deviceId);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("删除模型失败");
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 批量删除模型
     *
     * @param deviceIds 设备id数组
     * @return
     */
    @Override
    public boolean deleteModels(String[] deviceIds) {
        int isNum = deviceMapper.deleteBatchIds(Arrays.asList(deviceIds));
        boolean isSuccess;
        if (isNum == deviceIds.length) {
            isSuccess = true;
        } else {
            log.error("批量删除模型失败");
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 获取上传模型链接
     *
     * @param extension 文件扩展名
     * @return
     */
    @Override
    public String getUploadModelUrl(String extension) {
        MinioUtils minioUtils = new MinioUtils();
        String uploadPath = null;
        try {
            uploadPath = minioUtils.getUploadPath(extension);
        } catch (Exception e) {
            log.error("获取上传文件链接异常：{}", e);
        }
        return uploadPath;
    }

    /**
     * 添加模型
     *
     * @param device 模型实体类
     * @return
     */
    @Override
    public boolean addModel(Device device) {
        int isNum = deviceMapper.insert(device);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("添加模型失败");
            isSuccess = false;
        }
        return isSuccess;
    }
}
