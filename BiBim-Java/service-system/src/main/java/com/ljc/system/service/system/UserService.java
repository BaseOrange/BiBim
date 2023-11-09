package com.ljc.system.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljc.model.pojo.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dachengzi
 * @date 2022-12-25 22:51
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名称查询数据库
     *
     * @param username 用户名
     * @return
     */
    User getUserInfoByUserName(String username);

    /**
     * 注册用户
     *
     * @param user 用户实体类
     * @return
     */
    boolean registerUser(User user);

    /**
     * 获取用户列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    HashMap<String, Object> getUserMap(Long current, Long size);

    /**
     * 修改用户
     *
     * @param user 用户实体类
     * @return
     */
    boolean updateUser(User user);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return
     */
    boolean deleteUser(String userId);

    /**
     * 重置用户密码
     *
     * @param userId 用户id
     * @return
     */
    boolean resetUser(String userId);
}
