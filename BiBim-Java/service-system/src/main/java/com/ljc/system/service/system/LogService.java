package com.ljc.system.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljc.model.pojo.LoginLog;
import com.ljc.model.pojo.OperLog;

import java.util.List;
import java.util.Map;


/**
 * 日志服务类
 *
 * @author dachengzi
 * @since 2023-02-07
 */
public interface LogService {
    /**
     * 获取登录日志
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    Map<String, Object> getLoginLogList(Long current, Long size);

    /**
     * 获取操作日志
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    Map<String, Object> getOperLogList(Long current, Long size);

    /**
     * 根据id获取操作日志信息
     *
     * @param id
     * @return
     */
    OperLog getOperLogInfo(String id);

    /**
     * 根据id获取登录日志信息
     *
     * @param id
     * @return
     */
    LoginLog getLoginLogInfo(String id);

    /**
     * 获取服务器状态
     *
     * @return
     */
    Map<String, String> getServerStateMap() throws InterruptedException;
}
