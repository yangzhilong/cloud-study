package com.longge.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 分页返回对象
 * @author roger yang
 * @date 11/22/2019
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResultDto<T> {
    /**
     * 页码
     */
    private Long pageNum;
    /**
     * 每页记录数
     */
    private Long pageSize;
    /**
     * 总页数
     */
    private Long pageCount;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 数据记录
     */
    private List<T> dataList;
    
    /**
     * 全参数build
     * @param pageNum
     * @param pageSize
     * @param pageCount
     * @param recodeCount
     * @param dataList
     * @return
     */
    public static <T> PageResultDto<T> buildAll(Long pageNum, Long pageSize, Long pageCount, Long recodeCount, List<T> dataList) {
        PageResultDto<T> result = new PageResultDto<T>(pageNum, pageSize, pageCount,recodeCount, dataList);
        if(0 == recodeCount%pageSize) {
            result.setPageCount(recodeCount / pageSize);
        } else {
            result.setPageCount(recodeCount / pageSize + 1);
        }
        
        return result;
    }
    
    /**
     * 带分页PageParamDto的build
     * @param pageParam
     * @param pageCount
     * @param recodeCount
     * @param dataList
     * @return
     */
    public static <T> PageResultDto<T> build(PageParamDto pageParam, Long pageCount, Long recodeCount, List<T> dataList) {
        PageResultDto<T> result = new PageResultDto<T>(pageParam.getPageNum(), pageParam.getPageSize(), pageCount,recodeCount, dataList);
        if(0 == recodeCount%result.getPageSize()) {
            result.setPageCount(recodeCount / result.getPageSize());
        } else {
            result.setPageCount(recodeCount / result.getPageSize() + 1);
        }
        return result;
    }
}
