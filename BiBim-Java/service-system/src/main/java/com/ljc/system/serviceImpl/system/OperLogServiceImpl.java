package com.ljc.system.serviceImpl.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljc.model.pojo.OperLog;
import com.ljc.system.mapper.OperLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljc.system.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作日志记录 服务实现类
 *
 * @author dachengzi
 * @since 2023-01-16
 */
@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog> implements OperLogService {
    @Autowired
    private OperLogMapper operLogMapper;

    /**
     * 向数据库插入操作日志
     *
     * @param operLog 操作日志
     */
    @Override
    public void saveOperLog(OperLog operLog) {
        operLogMapper.insert(operLog);
    }
}
