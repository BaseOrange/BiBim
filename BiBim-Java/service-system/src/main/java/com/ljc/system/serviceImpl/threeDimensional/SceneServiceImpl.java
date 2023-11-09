package com.ljc.system.serviceImpl.threeDimensional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljc.model.pojo.DeviceDeploy;
import com.ljc.model.pojo.Scene;
import com.ljc.system.mapper.SceneMapper;
import com.ljc.system.service.threeDimensional.SceneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 场景服务实现类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
@Service
public class SceneServiceImpl extends ServiceImpl<SceneMapper, Scene> implements SceneService {

    @Autowired
    private SceneMapper sceneMapper;

    /**
     * 获取场景列表
     *
     * @param current 页数
     * @param size    每页数量
     * @return
     */
    @Override
    public HashMap<String, Object> getSceneList(Long current, Long size) {
        Page<Scene> scenePage = new Page<>(current, size);
        Page<Scene> sceneList = sceneMapper.selectPage(scenePage, null);

        Integer count = sceneMapper.selectCount(null);

        HashMap<String, Object> sceneMap = new HashMap<>();
        sceneMap.put("count",count);
        sceneMap.put("list",sceneList.getRecords());
        return sceneMap;
    }

    /**
     * 删除场景
     *
     * @param sceneId 场景id
     * @return
     */
    @Override
    public boolean deleteScene(Long sceneId) {
        int isNum = sceneMapper.deleteById(sceneId);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("删除场景失败");
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 批量删除场景
     *
     * @param sceneIds 场景id数组
     * @return
     */
    @Override
    public boolean deleteScenes(String[] sceneIds) {
        int isNum = sceneMapper.deleteBatchIds(Arrays.asList(sceneIds));
        boolean isSuccess;
        if (isNum == sceneIds.length) {
            isSuccess = true;
        } else {
            log.error("批量删除模型失败");
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 修改场景
     *
     * @param scene 场景实体类
     * @return
     */
    @Override
    public boolean updateScene(Scene scene) {
        int isNum = sceneMapper.updateById(scene);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("修改场景失败");
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 添加场景
     *
     * @param scene 场景实体类
     * @return
     */
    @Override
    public boolean addScene(Scene scene) {
        int isNum = sceneMapper.insert(scene);
        boolean isSuccess;
        if (isNum == 1) {
            isSuccess = true;
        } else {
            log.error("添加场景失败");
            isSuccess = false;
        }
        return isSuccess;
    }
}
