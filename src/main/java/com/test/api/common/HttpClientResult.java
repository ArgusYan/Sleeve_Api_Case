package com.test.api.common;

import lombok.*;

import java.io.Serializable;

/**
 * @author Argus
 * @className HttpClientResult
 * @description: 封装httpClient响应结果
 * @date 2020/4/4 15:43
 * @Version V1.0
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HttpClientResult implements Serializable {
    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult(int code) {
        this.code = code;
    }
}