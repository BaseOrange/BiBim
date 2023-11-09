package com.ljc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljc.model.pojo.OperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志记录 Mapper 接口
 *
 * @author dachengzi
 * @since 2023-01-16
 */
@Mapper
public interface OperLogMapper extends BaseMapper<OperLog> {

}
