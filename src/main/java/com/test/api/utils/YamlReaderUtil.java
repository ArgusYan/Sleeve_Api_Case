/**
 * @ClassName YamlReaderUtil
 * @description: TODO
 * @author Argus
 * @Date 2020/3/5 22:57
 * @Version V1.0
 */
package com.test.api.utils;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;


public class YamlReaderUtil {
    static Map properties;

    static {
        readYml("properties/application.yml");
    }



    /**
     * 读取yml
     * @param key
     */
    public static void readYml(String key) {
        try {
            Yaml yaml = new Yaml();
            URL url = YamlReaderUtil.class.getClassLoader().getResource(key);
            if (url != null) {
                //获取test.yaml文件中的配置数据，然后转换为obj，
//                Object obj = yaml.load(new FileInputStream(url.getFile()));
                //也可以将值转换为Map
                properties = (Map) yaml.load(new FileInputStream(url.getFile()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 自定义路径名称获取配置基础请求路径
     *
     * @return url
     */
    public static Object getAttribute(String attributeKey) {
        Object attr =null;
        try {
            attr = YamlReaderUtil.properties.get(attributeKey);
            if (attr == null) {
                System.out.println("尚未配置"+attributeKey);
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attr;
    }

    public static String getBaseUrl() {
        String url = "";
        try {
            url = (String) properties.get("baseUrl");
            if (StringUtils.isEmpty(url)) {
                System.out.println("尚未配置BaseUrl");
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }

    public static void main(String[] args) {
        int serverPort = (int)YamlReaderUtil.getAttribute("serverPort");
        System.out.println(serverPort);
    }
}
