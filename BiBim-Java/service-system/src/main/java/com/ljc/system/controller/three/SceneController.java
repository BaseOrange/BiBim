package com.ljc.system.controller.three;


import com.ljc.common.result.Result;
import com.ljc.common.utils.JwtHelper;
import com.ljc.model.enums.BusinessType;
import com.ljc.model.pojo.Scene;
import com.ljc.system.annotation.Log;
import com.ljc.system.service.threeDimensional.SceneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 场景控制器
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Api(tags = "场景接口")
@RestController
@RequestMapping("/three/scene")
@Slf4j
public class SceneController {
    @Autowired
    private SceneService sceneService;

    /**
     * 获取场景列表
     *
     * @return
     */
    @Log(title = "场景管理", businessType = BusinessType.SELECT)
    @ApiOperation("获取场景列表")
    @GetMapping("/getSceneList/{current}/{size}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    public Result getSceneList(@PathVariable("current") Long current, @PathVariable("size") Long size) {
        log.info("【获取场景列表请求】current：{}，size：{}", current, size);
        HashMap<String, Object> sceneMap = sceneService.getSceneList(current, size);
        log.info("【获取场景列表响应】sceneMap：{}", sceneMap);
        return Result.ok(sceneMap);
    }

    /**
     * 获取场景信息
     *
     * @return
     */
    @Log(title = "场景管理", businessType = BusinessType.SELECT)
    @ApiOperation("获取场景信息")
    @GetMapping("/getSceneInfo/{sceneId}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER','USER')")
    public Result getSceneList(@PathVariable("sceneId") Long sceneId) {
        log.info("【获取场景信息请求】sceneId：{}", sceneId);
        Scene scene = sceneService.getById(sceneId);
        log.info("【获取场景信息响应】scene：{}", scene);
        return Result.ok(scene);
    }

    /**
     * 删除场景
     *
     * @param sceneId 场景id
     * @return
     */
    @Log(title = "场景管理", businessType = BusinessType.DELETE)
    @ApiOperation("删除场景")
    @DeleteMapping("/deleteScene")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result deleteScene(Long sceneId) {
        log.info("【删除场景请求】sceneId：{}", sceneId);
        boolean isSuccess = sceneService.deleteScene(sceneId);
        log.info("【删除场景响应】isSuccess：{}", isSuccess);
        if (isSuccess == true) {
            return Result.ok("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 批量删除场景
     *
     * @param sceneIdStr 场景id数组
     * @return
     */
    @Log(title = "场景管理", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除场景")
    @DeleteMapping("/deleteScenes/{sceneIds}")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result deleteScenes(@PathVariable("sceneIds") String sceneIdStr) {
        log.info("【批量删除场景请求】sceneIdStr：{}", sceneIdStr);
        String[] sceneIds = sceneIdStr.split(",");
        boolean isSuccess = sceneService.deleteScenes(sceneIds);
        log.info("【批量删除场景响应】isSuccess：{}", isSuccess);
        if (isSuccess) {
            return Result.ok("批量删除成功");
        } else {
            return Result.fail("批量删除失败");
        }
    }

    /**
     * 修改场景
     *
     * @param scene 场景实体类
     * @return
     */
    @Log(title = "场景管理", businessType = BusinessType.UPDATE)
    @ApiOperation("修改场景场景")
    @PutMapping("/updateScene")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result updateScene(@RequestBody Scene scene) {
        log.info("【修改场景请求】scene：{}", scene);
        boolean isSuccess = sceneService.updateScene(scene);
        log.info("【修改场景响应】isSuccess：{}", isSuccess);
        if (isSuccess) {
            return Result.ok("修改场景成功");
        } else {
            return Result.fail("修改场景失败");
        }
    }

    /**
     * 添加场景
     *
     * @param scene 场景实体类
     * @return
     */
    @Log(title = "场景管理", businessType = BusinessType.INSERT)
    @ApiOperation("添加场景")
    @PostMapping("/addScene")
    @PreAuthorize("hasAnyAuthority('ROOT','ADMIN','SCENER')")
    public Result addScene(HttpServletRequest request, @RequestBody Scene scene) {
        // 获取请求头的token
        String token = request.getHeader("Authorization");
        // 从token中获取用户名称
        String userId = JwtHelper.getUserId(token);

        log.info("【添加场景请求】scene：{}", scene);
        scene.setCreateUserId(Long.parseLong(userId));
        boolean isSuccess = sceneService.addScene(scene);
        log.info("【添加场景响应】isSuccess：{}", isSuccess);
        if (isSuccess) {
            return Result.ok("添加场景成功");
        } else {
            return Result.fail("添加场景失败");
        }
    }
}
