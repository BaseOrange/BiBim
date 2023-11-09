package com.ljc.system.serviceImpl.threeDimensional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ljc.blockchain.BlockChainUtils;
import com.ljc.common.result.Result;
import com.ljc.model.enums.BusinessType;
import com.ljc.model.pojo.DeviceDeploy;
import com.ljc.model.vo.DepositoryVo;
import com.ljc.model.vo.DeviceInfoVo;
import com.ljc.system.annotation.Log;
import com.ljc.system.mapper.DeviceDeployMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljc.system.service.threeDimensional.DeviceDeployService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 设部部署服务实现类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Service
@Slf4j
public class DeviceDeployServiceImpl extends ServiceImpl<DeviceDeployMapper, DeviceDeploy> implements DeviceDeployService {
    @Autowired
    private DeviceDeployMapper deviceDeployMapper;

    /**
     * 获取场景设备部署信息
     *
     * @param sceneId 场景id
     * @return
     */
    @Override
    public List<DeviceDeploy> getSceneDeviceDeployInfo(Long sceneId) {
        List<DeviceDeploy> deviceDeployList = deviceDeployMapper.getSceneDeviceDeployInfo(sceneId);
        return deviceDeployList;
    }

    /**
     * 更新部署设备信息
     *
     * @param deviceDeployList 部署设备列表
     * @return
     */
    @Override
    public boolean updataDeviceDeployInfo(List<DeviceDeploy> deviceDeployList) {
        int count = 0;
        for (DeviceDeploy deviceDeploy : deviceDeployList) {
            long id = deviceDeploy.getId();
            if (id >= 0) {
                // 小于零为新添加模型不删除
                deviceDeployMapper.deleteById(id);
            }
            int insertCount = deviceDeployMapper.insert(deviceDeploy);
            count += insertCount;
        }
        if (count == deviceDeployList.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除部署设备
     *
     * @param deviceDeployId 部署设备id
     * @return
     */
    @Override
    public boolean deleteDeviceDeploy(Long deviceDeployId) {
        int isNum = deviceDeployMapper.deleteById(deviceDeployId);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("删除部署设备失败");
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 更新设备状态信息
     *
     * @param deviceInfoVo 设备状态信息VO
     * @return
     */
    @Override
    public boolean updataDeviceInfo(DeviceInfoVo deviceInfoVo) {
        int count = deviceDeployMapper.updateDeviceInfo(deviceInfoVo);
        DepositoryVo depositoryVo = new DepositoryVo();
        // cont = deviceInfoVo.getUsername() + "操作设备";
        depositoryVo.setContent(deviceInfoVo.getDeviceInfo());
        depositoryVo.setUserId(deviceInfoVo.getUserId());

        depositoryVo.setTime(String.valueOf(System.currentTimeMillis()));

        depositoryVo.setName(deviceInfoVo.getUsername());
        // 获取hash
        depositoryVo.setHash(hashKeyForDisk(deviceInfoVo.getDeviceInfo() + "|" + deviceInfoVo.getUserId()));
        try {
            String res = BlockChainUtils.invokeDepositoryContract(depositoryVo);
            log.info("上链返回信息{}", res);
        } catch (Exception e) {
            log.error("执行合约失败{}", e);
        }

        if (count == 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取Hash值
     */
    private static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            //Java利用MessageDigest获取字符串MD5
            final MessageDigest mDigestData = MessageDigest.getInstance("MD5");
            mDigestData.update(key.getBytes());
            cacheKey = bytesToHexString(mDigestData.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
