package com.lyt.logonlinemonitor.sys.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义响应结构
 * @author  cp
 */
@Getter
@Setter
public class ResultObj {
    /**
     * 响应业务状态
     * 200	成功
     * 201	错误
     * 400	参数错误
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    public static ResultObj error(Integer status, String msg, Object data) {
        return new ResultObj(status, msg, data);
    }

    public static ResultObj ok(Object data) {
        return new ResultObj(data);
    }

    public static ResultObj ok() {
        return ok(null);
    }

    public static ResultObj error(Integer status, String msg) {
        return new ResultObj(status, msg, null);
    }

    private ResultObj(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ResultObj(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

}