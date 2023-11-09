package com.ljc.system.controller.system;

import com.ljc.common.result.Result;
import com.ljc.common.utils.JwtHelper;
import com.ljc.model.constants.StatusConst;
import com.ljc.model.enums.ResultCodeEnum;
import com.ljc.model.pojo.User;
import com.ljc.model.vo.LoginVo;
import com.ljc.system.custom.CustomPassword;
import com.ljc.system.exception.BiBimException;
import com.ljc.system.service.system.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dachengzi
 * @date 2023-01-11 00:34
 */
@Api(tags = "用户登录接口")
@RestController
@RequestMapping("/system/admin")
public class IndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomPassword customPassword;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        // 查询用户名在数据库中
        User user = userService.getUserInfoByUserName(loginVo.getUsername());

        // 判断数据库是否为空
        if (user == null){
            throw new BiBimException(ResultCodeEnum.USER_ERROR_A0201);
        }

        // 判断密码是否正确
        String password = loginVo.getPassword();
        String bCryptPassword = customPassword.encode(password);
        if (!customPassword.matches(user.getPassword(),bCryptPassword)){
            throw new BiBimException(ResultCodeEnum.USER_ERROR_A0210);
        }

        //判断用户是否可用
        if (user.getStatus().intValue() == StatusConst.DISABLE){
            throw new BiBimException(ResultCodeEnum.USER_ERROR_A0202);
        }

        //根据id和名称生成token，通过map返回
        String token = JwtHelper.createToken(user.getId().toString(), user.getUsername());

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("token", token);
        return Result.ok(loginInfo);
    }
}
