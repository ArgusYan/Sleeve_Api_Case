package com.test.api.dto;

import lombok.*;

import java.util.Map;

/**
 * @author Argus
 * @className ClientDTO
 * @description: TODO
 * @date 2020/4/10 15:27
 * @Version V1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private String url;
    private Map<String, String> headers;
    private Map<String, String> params;
    private String jsonStr;
}