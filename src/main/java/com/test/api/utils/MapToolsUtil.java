/**
 * @ClassName MapToolsUtil
 * @description: TODO
 * @author Argus
 * @Date 2020/3/6 11:00
 * @Version V1.0
 */
package com.test.api.utils;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Map转换工具类
 */
public class MapToolsUtil {

    public static Map<String,Object> objectToHashMap(Object obj) {
        if (obj == null) {return null;}
        BeanMap beanMap = new BeanMap(obj);
        return beanMapToHashMap(beanMap);
    }

    @SuppressWarnings("unchecked")
    public static Map<String,Object> beanMapToHashMap(BeanMap beanMap) {
        Map<String, Object> map = new HashMap<>();

        for (Object o : beanMap.entrySet()) { // It's not parameterized :(
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) o;
            String key = entry.getKey();
            Object value = entry.getValue();
            map.put(key, value);
        }
        return map;
    }


    @SuppressWarnings("unchecked")
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();

        org.apache.commons.beanutils.BeanUtils.populate(obj, map);

        return obj;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseMapForFilterNull(Map<String, Object> map) {

        return Optional.ofNullable(map).map(
                (v) -> {
                    Map params = v.entrySet().stream()
                            .filter((e) -> checkValueNull(e.getValue()))
                            .collect(Collectors.toMap(
                                    (e) -> (String) e.getKey(),
                                    (e) -> e.getValue()
                            ));

                    return params;
                }
        ).orElse(null);
    }


    private static boolean checkValueNull(Object object) {

        if (object instanceof String && "".equals(object)) {
            return false;
        }

        if (null == object) {
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseMapForFilterOptional(Map<String, Object> map,String[] keys) {

        return Optional.ofNullable(map).map(
                (v) -> {
                    Map params = v.entrySet().stream()
                            .filter((e) -> filterOptional(e.getKey(),keys))
                            .collect(Collectors.toMap(
                                    (e) -> (String) e.getKey(),
                                    (e) -> e.getValue()
                            ));

                    return params;
                }
        ).orElse(null);
    }

    private static boolean filterOptional(String key,String[] keys) {
        for (String _key : keys) {
            if (_key.toLowerCase().equals(key.toLowerCase())) {return true;}
        }
        return false;
    }


    public static Map<String, String> transformMapStringWithObj(Map<String, Object> map) {
        if (map == null) {return null; }
        Map<String,String> _map = Maps.newHashMap();
        for (String key : map.keySet()) {
            if (map.get(key) instanceof String) {
                String value = (String)map.get(key);
                _map.put(key,value);
            }
            if(map.get(key) instanceof Integer) {
                String value = map.get(key).toString();
                _map.put(key,value);
            }
        }
        return _map;
    }



}
