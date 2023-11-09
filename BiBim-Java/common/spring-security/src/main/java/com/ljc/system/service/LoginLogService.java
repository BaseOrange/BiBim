package com.ljc.system.service;

/**
 * @author dachengzi
 * @Date 2023/1/16 22:27
 */
public interface LoginLogService {
    public void recordLoginLog(String username,Integer status,String ipaddr,String message);
}
