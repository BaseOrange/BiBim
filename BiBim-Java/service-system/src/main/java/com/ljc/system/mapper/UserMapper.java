package com.ljc.system.mapper;

import com.ljc.model.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  用户 Mapper 接口
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
