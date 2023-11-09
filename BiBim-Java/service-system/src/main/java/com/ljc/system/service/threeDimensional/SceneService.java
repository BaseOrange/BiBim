package com.ljc.system.service.threeDimensional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljc.model.pojo.DeviceDeploy;
import com.ljc.model.pojo.Scene;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * 场景服务类
 *
 * @author dachengzi
 * @since 2023-01-13
 */
public interface SceneService extends IService<Scene> {

    /**
     * 获取场景列表
     *
     * @param current 页数
     * @param size    每页数量
     * @return
     */
    HashMap<String, Object> getSceneList(Long current, Long size);

    /**
     * 删除场景
     *
     * @param sceneId 场景id
     * @return
     */
    boolean deleteScene(Long sceneId);

    /**
     * 批量删除场景
     *
     * @param sceneIds 场景id数组
     * @return
     */
    boolean deleteScenes(String[] sceneIds);

    /**
     * 修改场景
     *
     * @param scene 场景实体类
     * @return
     */
    boolean updateScene(Scene scene);

    /**
     * 添加场景
     *
     * @param scene 场景实体类
     * @return
     */
    boolean addScene(Scene scene);
}
