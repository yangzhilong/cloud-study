package com.longge.common.api.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页请求时的分页信息
 * @author yangzl
 * @data 2019年4月25日
 *
 */
public class PageParamDto implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -4894853330111808862L;
    /**
     * 页码
     */
    @Min(1)
    @Getter
    @Setter
    private Long pageNum;
    /**
     * 每页记录数
     */
    @Getter
    @Setter
    private Long pageSize;
        
    /**
     * 计算起始行
     * @return
     */
    public Long getStartRow() {
        return pageNum * pageNum - 1;
    }
}
