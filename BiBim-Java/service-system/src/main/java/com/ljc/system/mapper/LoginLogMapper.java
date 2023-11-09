package com.ljc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljc.model.pojo.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志表 Mapper 接口
 *
 * @author dachengzi
 * @since 2023-01-16
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

}
