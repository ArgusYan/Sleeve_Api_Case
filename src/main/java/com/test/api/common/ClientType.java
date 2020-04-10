package com.test.api.common;

import lombok.Getter;
import lombok.Setter;

import java.util.stream.Stream;

/**
 * @author Argus
 * @className ClientType
 * @description: TODO
 * @date 2020/4/10 15:38
 * @Version V1.0
 */
public class ClientType {
    public static final String DO_GET = "DO_GET";
    public static final String DO_GET_WITHOUT_HEADERS_AND_PARAMS="DO_GET_WITHOUT_HEADERS_AND_PARAMS";
    public static final String DO_GET_WITH_HEADERS_WITHOUT_PARAMS="DO_GET_WITH_HEADERS_WITHOUT_PARAMS";
    public static final String DO_GET_WITHOUT_HEADERS_WITH_PARAMS="DO_GET_WITHOUT_HEADERS_WITH_PARAMS";
    public static final String DO_POST="DO_POST";
    public static final String DO_POST_WITHOUT_HEADERS_AND_PARAMS="DO_POST_WITHOUT_HEADERS_AND_PARAMS";
    public static final String DO_POST_WITHOUT_HEADERS_WITH_PARAMS="DO_POST_WITHOUT_HEADERS_WITH_PARAMS";
    public static final String DO_POST_WITH_HEADERS_WITHOUT_PARAMS="DO_POST_WITH_HEADERS_WITHOUT_PARAMS";
    public static final String DO_POST_WITH_HEADERS_WITH_JSON="DO_POST_WITH_HEADERS_WITH_JSON";
    public static final String DO_POST_WITHOUT_HEADERS_WITH_JSON="DO_POST_WITHOUT_HEADERS_WITH_JSON";

}
