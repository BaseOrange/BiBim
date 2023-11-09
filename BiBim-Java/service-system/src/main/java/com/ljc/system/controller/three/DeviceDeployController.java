package com.ljc.system.controller.three;


import com.alibaba.fastjson.JSON;
import com.ljc.common.result.Result;
import com.ljc.common.utils.JwtHelper;
import com.ljc.model.enums.BusinessType;
import com.ljc.model.pojo.DeviceDeploy;
import com.ljc.model.vo.DeviceInfoVo;
import com.ljc.system.annotation.Log;
import com.ljc.system.service.threeDimensional.DeviceDeployService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 设备部署控制器
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Api(tags = "设备部署接口")
@RestController
@RequestMapping("/three/deviceDeploy")
@Slf4j
public class DeviceDeployController {
    @Autowired
    private DeviceDeployService deviceDeployService;

    /**
     * 获取场景设备部署信息
     *
     * @param sceneId 场景id
     * @return
     */
    @Log(title = "场景管理", businessType = BusinessType.SELECT)
    @ApiOperation("获取场景设备部署信息")
    @GetMapping("/getSceneDeviceDeployInfo/{sceneId}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    public Result getSceneDeviceDeployInfo(@PathVariable("sceneId") Long sceneId) {
        log.info("【获取场景信息请求】sceneId：{}", sceneId);
        List<DeviceDeploy> deviceDeployList = deviceDeployService.getSceneDeviceDeployInfo(sceneId);
        log.info("【获取场景信息响应】sceneInfo：{}", deviceDeployList);
        return Result.ok(deviceDeployList);
    }

    /**
     * 获取部署设备信息
     *
     * @param deviceDeployId 部署设备id
     * @return
     */
    @Log(title = "设备管理", businessType = BusinessType.SELECT)
    @ApiOperation("获取部署设备信息")
    @GetMapping("/getDeviceDeployInfo/{deviceDeployId}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    public Result getDeviceDeployInfo(@PathVariable("deviceDeployId") Long deviceDeployId) {
        log.info("【获取设备信息请求】deviceDeployId：{}", deviceDeployId);
        DeviceDeploy deviceDeploy = deviceDeployService.getById(deviceDeployId);
        log.info("【获取设备信息响应】deviceDeploy：{}", deviceDeploy);
        return Result.ok(deviceDeploy);
    }

    /**
     * 更新部署设备信息
     *
     * @param deviceDeployList 部署设备列表
     * @return
     */
    @Log(title = "设备管理", businessType = BusinessType.SELECT)
    @ApiOperation("更新部署设备信息")
    @PostMapping("/updataDeviceDeployInfo")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result updataDeviceDeployInfo(@RequestBody List<DeviceDeploy> deviceDeployList) {
        log.info("【获取更新部署设备信息请求】deviceDeployList：{}", JSON.toJSONString(deviceDeployList));
        boolean isSuccess = deviceDeployService.updataDeviceDeployInfo(deviceDeployList);
        log.info("【获取更新部署设备信息响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("修改成功");
        } else {
            return Result.fail("修改失败");
        }
    }

    /**
     * 删除部署设备
     *
     * @param deviceDeployId 部署设备id
     * @return
     */
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
    @ApiOperation("删除部署设备")
    @DeleteMapping("/deleteDeviceDeploy")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result deleteDeviceDeploy(Long deviceDeployId) {
        log.info("【删除部署设备请求】deviceDeployId：{}", deviceDeployId);
        boolean isSuccess = deviceDeployService.deleteDeviceDeploy(deviceDeployId);
        log.info("【删除部署设备响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 更新设备状态信息
     *
     * @param deviceInfoVo 设备状态信息VO
     * @return
     */
    @Log(title = "设备管理", businessType = BusinessType.SELECT)
    @ApiOperation("更新设备状态信息")
    @PostMapping("/updataDeviceInfo")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    public Result updataDeviceInfo(HttpServletRequest request, @RequestBody DeviceInfoVo deviceInfoVo) {
        // 获取请求头的token
        String token = request.getHeader("Authorization");
        // 从token中获取用户名和id
        deviceInfoVo.setUserId(JwtHelper.getUserId(token));
        deviceInfoVo.setUsername(JwtHelper.getUsername(token));

        log.info("【获取更新设备状态信息请求】deviceInfoVo：{}", deviceInfoVo);
        boolean isSuccess = deviceDeployService.updataDeviceInfo(deviceInfoVo);
        log.info("【获取更新设备状态信息响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("更新成功");
        } else {
            return Result.fail("更新失败");
        }
    }
}
