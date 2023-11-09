package com.ljc.model.enums;

/**
 * 业务操作类型
 *
 * @author dachengzi
 * @Date 2023/1/20 19:47
 */
public enum BusinessType {
    /**
     * 其它
     */
    OTHER,
    /**
     * 查询
     */
    SELECT,
    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    ASSGIN,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 更新状态
     */
    STATUS,

    /**
     * 清空数据
     */
    CLEAN
}
