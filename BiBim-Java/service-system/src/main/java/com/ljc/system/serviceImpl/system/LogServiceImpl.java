package com.ljc.system.serviceImpl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljc.model.pojo.LoginLog;
import com.ljc.model.pojo.OperLog;
import com.ljc.system.mapper.LoginLogMapper;
import com.ljc.system.mapper.OperLogMapper;
import com.ljc.system.service.system.LogService;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.platform.windows.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 日志实现类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private OperLogMapper operLogMapper;

    /**
     * 系统信息
     */
    private static SystemInfo systemInfo = new SystemInfo();
    /**
     * 硬件层
     */
    private static HardwareAbstractionLayer hardware = systemInfo.getHardware();


    /**
     * 获取登录日志
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @Override
    public Map<String, Object> getLoginLogList(Long current, Long size) {
        Page<LoginLog> loginLogPage = new Page<>(current, size);
        QueryWrapper<LoginLog> loginLogQueryWrapper = new QueryWrapper<>();
        loginLogQueryWrapper.orderByDesc("create_time");
        Page<LoginLog> loginLogList = loginLogMapper.selectPage(loginLogPage, loginLogQueryWrapper);

        // 获取总数
        Integer count = loginLogMapper.selectCount(null);

        HashMap<String, Object> loginLogMap = new HashMap<>();
        loginLogMap.put("count", count);
        loginLogMap.put("data", loginLogList.getRecords());

        return loginLogMap;
    }

    /**
     * 获取操作日志
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @Override
    public Map<String, Object> getOperLogList(Long current, Long size) {
        Page<OperLog> operLogPage = new Page<>(current, size);
        QueryWrapper<OperLog> operLogQueryWrapper = new QueryWrapper<>();
        operLogQueryWrapper.orderByDesc("create_time");
        Page<OperLog> operLogList = operLogMapper.selectPage(operLogPage, operLogQueryWrapper);

        // 获取总数
        Integer count = operLogMapper.selectCount(null);

        HashMap<String, Object> operLogMap = new HashMap<>();
        operLogMap.put("count", count);
        operLogMap.put("data", operLogList.getRecords());

        return operLogMap;
    }

    /**
     * 根据id获取操作日志信息
     *
     * @param id
     * @return
     */
    @Override
    public OperLog getOperLogInfo(String id) {
        OperLog operLog = operLogMapper.selectById(id);
        return operLog;
    }

    /**
     * 根据id获取登录日志信息
     *
     * @param id
     * @return
     */
    @Override
    public LoginLog getLoginLogInfo(String id) {
        LoginLog loginLog = loginLogMapper.selectById(id);
        return loginLog;
    }

    /**
     * 获取服务器状态
     *
     * @return
     */
    @Override
    public Map<String, String> getServerStateMap() throws InterruptedException {
        Map<String, String> serverStateMap = new LinkedMap(14);

        // GlobalMemory windowsGlobalMemory = new LinuxGlobalMemory();
        GlobalMemory windowsGlobalMemory = new WindowsGlobalMemory();
        long total = windowsGlobalMemory.getTotal();
        long available = windowsGlobalMemory.getAvailable();
        serverStateMap.put("phyMemory", new DecimalFormat("#.##").format(total * 1.0 / 1024 / 1024 / 1024) + "GB");
        serverStateMap.put("availablePhyMemory", new DecimalFormat("#.##").format(available * 1.0 / 1024 / 1024 / 1024) + "GB");
        serverStateMap.put("phyMemoryUseRate", new DecimalFormat("#.##").format((total - available) * 1.0 / total * 100));

        long swapTotal = windowsGlobalMemory.getSwapTotal();
        long swapUsed = windowsGlobalMemory.getSwapUsed();
        serverStateMap.put("virMemory", new DecimalFormat("#.##").format(swapTotal * 1.0 / 1024 / 1024 / 1024) + "GB");
        serverStateMap.put("useVirMemory", new DecimalFormat("#.##").format(swapUsed * 1.0 / 1024 / 1024 / 1024) + "GB");
        serverStateMap.put("virUseRate", new DecimalFormat("#.##").format((swapTotal - swapUsed) * 1.0 / swapTotal * 100));


        // CPU信息
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        TimeUnit.SECONDS.sleep(1);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        // cpu核数
        serverStateMap.put("cpuCoreCount", processor.getLogicalProcessorCount() + "");
        // cpu系统使用率
        Double cpuSysRate = cSys * 1.0 / totalCpu * 100;
        if (cpuSysRate < 0) {
            cpuSysRate = 1.0;
        }
        serverStateMap.put("cpuSysRate", new DecimalFormat("#.##").format(cpuSysRate));
        // cpu用户使用率
        serverStateMap.put("cpuUserRate", new DecimalFormat("#.##").format(user * 1.0 / totalCpu * 100));
        // cpu当前等待率
        serverStateMap.put("cpuWaitRate", new DecimalFormat("#.##").format(iowait * 1.0 / totalCpu * 100));
        // cpu当前使用率
        serverStateMap.put("cpuUseRate", new DecimalFormat("#.##").format((1.0 - (idle * 1.0 / totalCpu)) * 100));

        Properties props = System.getProperties();
        // 操作系统名
        serverStateMap.put("sysName", props.getProperty("os.name"));
        // 系统架构
        serverStateMap.put("sysFramework", props.getProperty("os.arch"));
        // 服务器名称
        // serverStateMap.put("服务器名称" + InetAddress.getLocalHost().getHostName());
        // 服务器Ip
        // serverStateMap.put("服务器Ip" + InetAddress.getLocalHost().getHostAddress());
        // 项目路径
        // serverStateMap.put("项目路径" + props.getProperty("user.dir"));

        return serverStateMap;
    }
}
