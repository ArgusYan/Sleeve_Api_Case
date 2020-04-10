package com.test.api.interfaces;

import com.test.api.common.ClientType;
import com.test.api.common.HttpClientResult;
import com.test.api.common.HttpClientToolAlter;
import com.test.api.dto.ClientDTO;
import lombok.*;

import java.util.Map;

/**
 * @author Argus
 * @className TokenVerify
 * @description: TODO
 * @date 2020/4/10 15:21
 * @Version V1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class InterfaceTokenVerify extends InterfaceBase {
    public static final String TYPE1 = ClientType.DO_POST_WITH_HEADERS_WITHOUT_PARAMS;
    public static final String TYPE2 = ClientType.DO_POST_WITHOUT_HEADERS_AND_PARAMS;

    public HttpClientResult client(String type) throws Exception {
        switch (type) {
            case InterfaceTokenVerify.TYPE1:
                this.httpClientResult = this.client01();
                break;
            case InterfaceTokenVerify.TYPE2:
                this.httpClientResult = this.client02();
                break;
            default:
                throw new RuntimeException();
        }
        return this.httpClientResult;
    }


    private HttpClientResult client01() throws Exception {
        this.setClientHeaders(this.clientParams);
        return HttpClientToolAlter.builder().clientParams(this.clientParams).build().http(ClientType.DO_POST_WITH_HEADERS_WITHOUT_PARAMS);
    }

    private HttpClientResult client02() throws Exception {
        return HttpClientToolAlter.builder().clientParams(this.clientParams).build().http(ClientType.DO_POST_WITHOUT_HEADERS_AND_PARAMS);

    }


}