package com.test.api.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Argus
 * @className JsonFormatUti
 * @description: 格式化JSON
 * @date 2020/4/7 11:34
 * @Version V1.0
 */
public class JsonFormatUtil {

    public static String jsonFormat(String jsonString) {
        JSONObject object = null;
        JSONArray jsonArray = null;
        String jsonstr = null;
        if (jsonString.substring(0,1).equals("{")) {
            object = JSONObject.parseObject(jsonString);
            jsonstr = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);

        }else {
            jsonArray = JSONObject.parseArray(jsonString);
            jsonstr = JSON.toJSONString(jsonArray, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);

        }

        return jsonstr;
    }





}