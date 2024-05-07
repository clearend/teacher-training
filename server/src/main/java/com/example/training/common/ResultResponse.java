package com.example.training.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import com.example.training.common.enums.StatusEnum;

@Getter
@Setter
public class ResultResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 接口响应状态码
     */
    private Integer code;

    /**
     * 接口响应信息
     */
    private String msg;

    /**
     * 接口响应的数据
     */
    private T data;

    /**
     * 封装成功响应的方法
     * @return response
     * @param <T> 响应数据类型
     */
    public static <T> ResultResponse<T> success() {
        ResultResponse<T> response = new ResultResponse<>();
        response.setMsg("请求成功");
        response.setCode(StatusEnum.SUCCESS.code);
        return response;
    }

    /**
     * 封装成功响应的方法
     * @param data 响应数据
     * @return response
     * @param <T> 响应数据类型
     */
    public static <T> ResultResponse<T> success(T data) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setMsg("请求成功");
        response.setData(data);
        response.setCode(StatusEnum.SUCCESS.code);
        return response;
    }

    /**
     * 封装error的响应
     * @param statusEnum error响应的状态值
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> error(StatusEnum statusEnum) {
        return error(statusEnum, statusEnum.message);
    }

    /**
     * 封装error的响应  可自定义错误信息
     * @param statusEnum error响应的状态值
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> error(StatusEnum statusEnum, String errorMsg) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setCode(statusEnum.code);
        response.setMsg(errorMsg);
        return response;
    }
}
