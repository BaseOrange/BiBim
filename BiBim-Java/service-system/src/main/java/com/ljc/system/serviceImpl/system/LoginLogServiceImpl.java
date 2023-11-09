package com.ljc.system.serviceImpl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljc.model.pojo.LoginLog;
import com.ljc.model.pojo.OperLog;
import com.ljc.system.mapper.LoginLogMapper;
import com.ljc.system.mapper.OperLogMapper;
import com.ljc.system.service.LoginLogService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 登录日志表 服务实现类
 *
 * @author dachengzi
 * @since 2023-01-16
 */
@Service
@Repository
@Mapper
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 记录日志
     *
     * @param username 用户名
     * @param status   登录状态
     * @param ipaddr   登录ip
     * @param message  登录信息
     */
    @Override
    public void recordLoginLog(String username, Integer status, String ipaddr, String message) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setStatus(status);
        loginLog.setIpaddr(ipaddr);
        loginLog.setMsg(message);
        loginLogMapper.insert(loginLog);
    }
}
