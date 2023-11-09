package com.ljc.system.exception;

import com.ljc.model.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 *
 * @author dachengzi
 * @date 2023-01-12 22:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiBimException extends RuntimeException{
    /**
     * 异常枚举
     */
    private ResultCodeEnum resultEnum;
}
