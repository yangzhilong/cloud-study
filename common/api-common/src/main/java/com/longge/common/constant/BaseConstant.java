package com.longge.common.constant;

/**
 * 常量的枚举定义接口，所有的常量必须实现它
 * @author yangzhilong
 * @param <T>
 */
public interface BaseConstant<T> {
    /**
     * code
     * @return
     */
	T getCode();
	/**
	 * 描述
	 * @return
	 */
    String getDesc();
}