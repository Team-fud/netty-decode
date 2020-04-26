package com.centerm.nettydecode.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Sheva
 * @date 2020/4/21 14:24
 * @description
 */
@Data
@AllArgsConstructor
public class Result {
    /**
     * 请求成功还是失败 成功 success 失败 error
     */
    private String status;
    /**
     *  返回具体信息
     */
    private String msg;
    /**
     * 返回的数据
     */
    private Object data;

}
