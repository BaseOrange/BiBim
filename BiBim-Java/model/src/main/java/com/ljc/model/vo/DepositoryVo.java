package com.ljc.model.vo;

import lombok.Data;

/**
 * 存证Vo
 *
 * @author dachengzi
 * @Date 2023/2/11 22:05
 */
@Data
public class DepositoryVo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String name;
    /**
     * hash代码
     */
    private String hash;
    /**
     * 操作内容
     */
    private String content;
    /**
     * 操作时间
     */
    private String time;
}
