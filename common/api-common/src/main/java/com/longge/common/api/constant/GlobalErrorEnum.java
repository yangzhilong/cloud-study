package com.longge.common.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局通用错误枚举
 * @author roger yang
 * @date 11/22/2019
 */
@AllArgsConstructor
public enum GlobalErrorEnum implements BaseConstant<String> {
    /**
     * 
     */
    PARAMETER_VERIFICATION_FAILED("400", "入参校验失败"),
    NOT_EXISTS_SERVICE("404", "服务或者页面不存在"),
    SYSTEM_ERROR("500","服务器内部错误"),
    ;

    @Getter
    private String code;
    @Getter
    private String desc;
}
