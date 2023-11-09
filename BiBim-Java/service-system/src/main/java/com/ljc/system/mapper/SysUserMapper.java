package com.ljc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljc.model.pojo.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author dachengzi
 * @date 2022-12-25 23:07
 */
@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<User> {
}
