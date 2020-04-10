package com.test.api.common;

import com.test.api.dto.ClientDTO;
import lombok.*;

import javax.xml.ws.ServiceMode;
import java.util.Map;

/**
 * @author Argus
 * @className HttpClientToolAlter
 * @description: TODO
 * @date 2020/4/10 15:34
 * @Version V1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpClientToolAlter extends HttpClientTool {
    private ClientDTO clientParams;

    public HttpClientResult http(String type) throws Exception {
        HttpClientResult httpClientResult = null;
        if (clientParams == null) {
            throw new RuntimeException("请求参数未添加！");
        }
        if (clientParams.getUrl() == null) {
            throw new RuntimeException("请求参数未添加！");
        }
        try {
            switch (type) {
                case ClientType.DO_GET:
                    httpClientResult = this.doGet(clientParams.getUrl(), clientParams.getHeaders(), clientParams.getParams());
                    break;
                case ClientType.DO_GET_WITH_HEADERS_WITHOUT_PARAMS:
                    httpClientResult = this.doGetWithHeadersWithoutParams(clientParams.getUrl(), clientParams.getHeaders());

                    break;
                case ClientType.DO_GET_WITHOUT_HEADERS_WITH_PARAMS:
                    httpClientResult = this.doGetWithoutHeadersWithParams(clientParams.getUrl(), clientParams.getParams());
                    break;
                case ClientType.DO_GET_WITHOUT_HEADERS_AND_PARAMS:
                    httpClientResult = this.doGetWithoutHeadersAndParams(clientParams.getUrl());
                    break;
                case ClientType.DO_POST:
                    httpClientResult = this.doPost(clientParams.getUrl(), clientParams.getHeaders(), clientParams.getParams());
                    break;
                case ClientType.DO_POST_WITH_HEADERS_WITH_JSON:
                    httpClientResult = this.doPostWithJsonWithHeaders(clientParams.getUrl(), clientParams.getHeaders(), clientParams.getJsonStr());
                    break;
                case ClientType.DO_POST_WITHOUT_HEADERS_WITH_JSON:
                    httpClientResult = this.doPostWithJsonWithoutHeaders(clientParams.getUrl(), clientParams.getJsonStr());
                    break;
                case ClientType.DO_POST_WITHOUT_HEADERS_AND_PARAMS:
                    httpClientResult = this.doPost(clientParams.getUrl(), null, null);
                    break;
                case ClientType.DO_POST_WITH_HEADERS_WITHOUT_PARAMS:
                    httpClientResult = this.doPostWithHeadersWithoutParams(clientParams.getUrl(), clientParams.getHeaders());
                    break;
                case ClientType.DO_POST_WITHOUT_HEADERS_WITH_PARAMS:
                    httpClientResult = this.doPostWithHeadersWithoutParams(clientParams.getUrl(), clientParams.getParams());
                    break;
                default:
                    throw new RuntimeException("未找到对应的请求方式，请添加！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpClientResult;
    }
}