package com.ljc.system.serviceImpl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljc.model.pojo.User;
import com.ljc.system.mapper.UserMapper;
import com.ljc.system.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现类
 *
 * @author dachengzi
 * @date 2022-12-25 22:53
 */
@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名称查询数据库
     */
    @Override
    public User getUserInfoByUserName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);
        return user;
    }

    /**
     * 注册用户
     *
     * @param user 用户实体类
     * @return
     */
    @Override
    public boolean registerUser(User user) {
        int isNum = userMapper.insert(user);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("注册用户失败");
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 获取用户列表
     *
     * @param current 页数
     * @param size    本页数量
     * @return
     */
    @Override
    public HashMap<String, Object> getUserMap(Long current, Long size) {
        Page<User> userPage = new Page<>(current, size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "username", "email", "telnumber", "status", "create_time", "update_time", "create_by", "update_by");
        Page<User> userList = userMapper.selectPage(userPage, queryWrapper);
        Integer count = userMapper.selectCount(null);
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("countTotal",count);
        userMap.put("userList",userList.getRecords());
        return userMap;
    }

    /**
     * 修改用户
     *
     * @param user 用户实体类
     * @return
     */
    @Override
    public boolean updateUser(User user) {
        user.setPassword(null);
        int isNum = userMapper.updateById(user);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("修改用户失败");
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public boolean deleteUser(String userId) {
        int isNum = userMapper.deleteById(userId);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("删除用户失败");
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 重置用户密码
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public boolean resetUser(String userId) {
        User userInfo = userMapper.selectById(userId);
        PasswordEncoder pe = new BCryptPasswordEncoder();
        // 生成密码
        String encodePassword = pe.encode(userInfo.getUsername() + "123**");

        User user = new User();
        user.setId(userInfo.getId());
        user.setPassword(encodePassword);

        int isNum = userMapper.updateById(user);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("重置密码失败");
            isSuccess = false;
        }
        return isSuccess;
    }
}
