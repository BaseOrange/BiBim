package com.ljc.system.controller.three;


import com.ljc.common.result.Result;
import com.ljc.common.utils.JwtHelper;
import com.ljc.model.enums.BusinessType;
import com.ljc.model.pojo.Device;
import com.ljc.model.pojo.DeviceDeploy;
import com.ljc.model.pojo.User;
import com.ljc.system.annotation.Log;
import com.ljc.system.service.threeDimensional.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 设备控制器
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Api(tags = "设备接口")
@RestController
@RequestMapping("/three/device")
@Slf4j
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    /**
     * 获取模型信息列表
     *
     * @param current 页数
     * @param size    本页数量
     * @retuqigern
     */
    @Log(title = "模型管理", businessType = BusinessType.SELECT)
    @ApiOperation("获取模型信息列表")
    @GetMapping("/getModelList/{current}/{size}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result getModelList(@PathVariable("current") Long current, @PathVariable("size") Long size) {
        HashMap<String, Object> deviceMap = deviceService.getModelList(current, size);
        log.info("【获取模型信息列表响应】deviceMap：{}", deviceMap);
        return Result.ok(deviceMap);
    }

    /**
     * 获取模型信息
     *
     * @param deviceId 设备id
     * @return
     */
    @Log(title = "模型管理", businessType = BusinessType.SELECT)
    @ApiOperation("获取模型信息")
    @GetMapping("/getModelInfo/{deviceId}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result getModelInfo(@PathVariable("deviceId") Long deviceId) {
        log.info("【获取模型信息请求】deviceId：{}", deviceId);
        Device deviceInfo = deviceService.getById(deviceId);
        log.info("【获取模型信息响应】deviceInfo：{}", deviceInfo);
        return Result.ok(deviceInfo);
    }

    /**
     * 删除模型
     *
     * @param deviceId 设备id
     * @return
     */
    @Log(title = "模型管理", businessType = BusinessType.SELECT)
    @ApiOperation("删除模型")
    @DeleteMapping("/deleteModel")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result deleteModel(Long deviceId) {
        log.info("【删除模型请求】deviceId：{}", deviceId);
        boolean isSuccess = deviceService.deleteModel(deviceId);
        log.info("【删除模型响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 批量删除模型
     *
     * @param modelIdStr 设备id数组
     * @return
     */
    @Log(title = "模型管理", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除模型")
    @DeleteMapping("/deleteModels")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result deleteModels(String modelIdStr) {
        log.info("【批量删除模型请求】modelIdStr：{}", modelIdStr);
        String[] sceneIds = modelIdStr.split(",");
        boolean isSuccess = deviceService.deleteModels(sceneIds);
        log.info("【批量删除模型响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 修改模型信息
     *
     * @param device 模型实体类
     * @return
     */
    @Log(title = "模型管理", businessType = BusinessType.SELECT)
    @ApiOperation("修改模型信息")
    @PutMapping("/updateModel")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result updateModel(@RequestBody Device device) {
        log.info("【修改模型信息请求】device：{}", device);
        boolean isSuccess = deviceService.updateById(device);
        log.info("【修改模型信息响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("修改成功");
        } else {
            return Result.fail("修改失败");
        }
    }

    /**
     * 获取上传模型链接
     *
     * @param extension 文件扩展名
     * @return
     */
    @Log(title = "模型管理", businessType = BusinessType.ASSGIN)
    @ApiOperation("获取上传模型链接")
    @GetMapping("/getUploadModelUrl")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result getUploadModelUrl(String extension) {
        log.info("【获取上传模型链接请求】extension：{}", extension);
        String uploadModelUrl = deviceService.getUploadModelUrl(extension);
        log.info("【获取上传模型链接响应】uploadModelUrl：{}", uploadModelUrl);
        return Result.ok(uploadModelUrl);
    }

    /**
     * 添加模型
     *
     * @param device 模型实体类
     * @return
     */
    @Log(title = "模型管理", businessType = BusinessType.INSERT)
    @ApiOperation("添加模型")
    @PostMapping("/addModel")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result addModel(HttpServletRequest request, @RequestBody Device device) {
        // 获取请求头的token
        String token = request.getHeader("Authorization");
        // 从token中获取用户名称
        String userId = JwtHelper.getUserId(token);

        log.info("【添加模型请求】device：{}", device);
        device.setCreateUserId(Long.parseLong(userId));
        boolean isSuccess = deviceService.addModel(device);
        log.info("【添加模型响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("添加成功");
        } else {
            return Result.fail("添加失败");
        }
    }
}
