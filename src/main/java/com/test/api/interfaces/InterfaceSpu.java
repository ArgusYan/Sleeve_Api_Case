package com.test.api.interfaces;

import com.test.api.common.ClientType;
import com.test.api.common.HttpClientResult;
import com.test.api.common.HttpClientToolAlter;
import com.test.api.dto.ClientDTO;
import lombok.*;

import java.util.Map;

/**
 * @author Argus
 * @className InterfaceSpuLatest
 * @description: TODO
 * @date 2020/4/10 17:04
 * @Version V1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class InterfaceSpu extends InterfaceBase {
    public static final String TYPE1 = ClientType.DO_GET;

    public HttpClientResult client(String type) throws Exception {
        switch (type) {
            case InterfaceSpu.TYPE1:
                this.httpClientResult = this.client01();
                break;
            default:
                throw new RuntimeException();
        }
        return httpClientResult;
    }


    private HttpClientResult client01() throws Exception {
        this.setClientHeaders(this.clientParams);
        return HttpClientToolAlter.builder().clientParams(this.clientParams).build().http(ClientType.DO_GET);
    }


}