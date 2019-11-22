package com.longge.common.api.dto;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Set;

import com.longge.common.api.constant.BaseConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 接口统一返回对象
 * @author roger yang
 * @date 11/22/2019
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalResponse<T> implements Serializable{
    private static final long serialVersionUID = 8753813726294333866L;
    private Boolean success;
    private String errorCode;
    private String errorMsg;
    private T data;
    
    /**
     * errorMsg可用使用{0}占位符来动态化错误消息
     * @param code
     * @param msg
     * @param formatArguments
     */
    public void setError(String code, String msg, Object... formatArguments) {
        success = Boolean.FALSE;
        errorCode = code;
        if(formatArguments.length > 0) {
            errorMsg = MessageFormat.format(msg, formatArguments);
        } else {
            errorMsg = msg;
        }
    }
    
    public void setError(BaseConstant<String> responseCode, Object... formatArguments) {
        setError(responseCode.getCode(), responseCode.getDesc(), formatArguments);
    }
    
    public void setError(String code, String msg, Set<? extends Object> detail) {
        setError(code, msg);
    }
    
    public void setError(BaseConstant<String> responseCode, Set<? extends Object> detail) {
        setError(responseCode.getCode(), responseCode.getDesc());
    }

    public static <T> GlobalResponse<T> buildSuccess() {
        return new GlobalResponse<T>(true, null, null, null);
    }
    
    public static <T> GlobalResponse<T> buildSuccess(T data) {
        return new GlobalResponse<T>(true, null, null, data);
    }
    
    public static <T> GlobalResponse<T> buildFail(BaseConstant<String> responseCode) {
        return new GlobalResponse<T>(false, responseCode.getCode(), responseCode.getDesc(), null);
    }

    public static <T> GlobalResponse<T> buildFail(BaseConstant<String> responseCode, String errorMsg) {
        return new GlobalResponse<T>(false, responseCode.getCode(), errorMsg, null);
    }
    
    public static <T> GlobalResponse<T> buildFail(String errorCode, String errorMsg) {
        return new GlobalResponse<T>(false, errorCode, errorMsg, null);
    }

    public static <T> GlobalResponse<T> buildAll(Boolean success, String code, String message, T data) {
        return new GlobalResponse<T>(success, code, message, data);
    }
    public static <T> GlobalResponse<T> buildFail(BaseConstant<String> responseCode,T data) {
        return new GlobalResponse<T>(false, responseCode.getCode(), null, data);
    }
}
