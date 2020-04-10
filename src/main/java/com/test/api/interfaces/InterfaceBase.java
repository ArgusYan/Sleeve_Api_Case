package com.test.api.interfaces;

import com.test.api.common.HttpClientResult;
import com.test.api.common.HttpClientToolAlter;
import com.test.api.dto.ClientDTO;
import lombok.*;

import java.util.Map;

/**
 * @author Argus
 * @className BaseApi
 * @description: TODO
 * @date 2020/4/10 16:45
 * @Version V1.0
 */
@Setter
@Getter
public abstract class InterfaceBase {
    public ClientDTO clientParams;
    public Map<String, String> headers;
    public HttpClientResult httpClientResult;

    public void setClientHeaders(ClientDTO clientParams) {
        try {
            this.headers = clientParams.getHeaders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}