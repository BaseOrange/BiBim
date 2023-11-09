package com.ljc.system.service;

import com.ljc.model.pojo.OperLog;

/**
 * 操作日志记录 服务类
 *
 * @author dachengzi
 * @Date 2023/1/20 21:14
 */
public interface OperLogService {
    /**
     * 向数据库插入操作日志
     *
     * @param operLog 操作日志
     */
    public void saveOperLog(OperLog operLog);
}
