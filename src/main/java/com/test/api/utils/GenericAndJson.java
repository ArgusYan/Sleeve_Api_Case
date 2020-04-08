package com.test.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * https://notemi.cn/entity-attribute-type-converter-spring-data-jpa-attributeconverter.html
 *
 * @author Argus
 * @className GenericAndJson
 * @description: 泛型JSON序列化与反序列化转换方法
 * @date 2020/3/17 23:23
 * @Version V1.0
 */
public class GenericAndJson {
    public static ObjectMapper mapper;

    private static class SingletonClassInstance {
        private static final ObjectMapper mapper = new ObjectMapper();
    }


    public static ObjectMapper getInstance() {
        return SingletonClassInstance.mapper;
    }


    /**
     * 转换为json字符串
     *
     * @param t 可以将比如List或Map或pojo转换成JSON
     * @return
     */
    public static <T> String ObjToJson(T t) {
        if (t == null) {return null;}
        try {
            return GenericAndJson.getInstance().writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



    /**
     * json数组转Obj或集合对象
     *
     * @param jsonStr
     * @param tr
     * @return
     */
    public static <T> T jsonToObjOrCollection(String jsonStr, TypeReference<T> tr) {
        if (StringUtils.isEmpty(jsonStr)) {return null;}
        try {
            return GenericAndJson.getInstance().readValue(jsonStr, tr);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



}