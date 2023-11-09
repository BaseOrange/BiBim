package com.ljc.system.controller.system;


import com.ljc.common.result.Result;
import com.ljc.model.enums.BusinessType;
import com.ljc.model.pojo.LoginLog;
import com.ljc.model.pojo.OperLog;
import com.ljc.system.annotation.Log;
import com.ljc.system.service.system.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 日志控制器
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Api(tags = "日志接口")
@RestController
@RequestMapping("/system/log")
@Slf4j
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * 获取登录日志
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @ApiOperation("获取登录日志")
    @GetMapping("/getLoginLog/{current}/{size}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
    @Log(title = "日志管理", businessType = BusinessType.SELECT)
    public Result getLoginLog(@PathVariable("current") Long current, @PathVariable("size") Long size) {
        log.info("【获取登录日志请求】current：{}，size：{}", current, size);
        Map<String, Object> loginLogMap = logService.getLoginLogList(current, size);
        log.info("【获取登录日志响应】loginLogMap：{}", loginLogMap);
        return Result.ok(loginLogMap);
    }

    /**
     * 获取操作日志
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @ApiOperation("获取操作日志")
    @GetMapping("/getOperaLog/{current}/{size}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
    @Log(title = "日志管理", businessType = BusinessType.SELECT)
    public Result getOperaLog(@PathVariable("current") Long current, @PathVariable("size") Long size) {
        log.info("【获取操作日志请求】current：{}，size：{}", current, size);
        Map<String, Object> operLogMap = logService.getOperLogList(current, size);
        log.info("【获取操作日志响应】operLogMap：{}", operLogMap);
        return Result.ok(operLogMap);
    }

    /**
     * 获取操作日志信息
     *
     * @param id
     * @return
     */
    @ApiOperation("获取操作日志信息")
    @GetMapping("/getOperaLogInfo/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
    @Log(title = "日志管理", businessType = BusinessType.SELECT)
    public Result getOperaLog(@PathVariable("id") String id) {
        log.info("【获取操作日志信息】id：{}", id);
        OperLog operLogInfo = logService.getOperLogInfo(id);
        log.info("【获取操作日志信息】operLogInfo：{}", operLogInfo);
        return Result.ok(operLogInfo);
    }

    /**
     * 获取登录日志信息
     *
     * @param id
     * @return
     */
    @ApiOperation("获取登录日志信息")
    @GetMapping("/getLoginLogInfo/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN')")
    @Log(title = "日志管理", businessType = BusinessType.SELECT)
    public Result getLoginLog(@PathVariable("id") String id) {
        log.info("【获取登录日志信息】id：{}", id);
        LoginLog loginLogInfo = logService.getLoginLogInfo(id);
        log.info("【获取登录日志信息】loginLogInfo：{}", loginLogInfo);
        return Result.ok(loginLogInfo);
    }

    /**
     * 获取服务器状态
     *
     * @return
     */
    @ApiOperation("获取服务器状态")
    @GetMapping("/getServerState")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    // @Log(title = "日志管理", businessType = BusinessType.SELECT)
    public Result getServerState() throws InterruptedException {
        log.info("【获取服务器状态请求】");
        Map<String, String> serverStateMap = logService.getServerStateMap();
        log.info("【获取服务器状态响应】serverStateMap：{}", serverStateMap);
        return Result.ok(serverStateMap);
    }
}
