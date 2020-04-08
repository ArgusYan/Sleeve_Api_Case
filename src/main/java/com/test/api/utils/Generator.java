package com.test.api.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Argus
 * @className Generator
 * @description: 逆向工程生成器
 * @date 2020/4/5 20:08
 * @Version V1.0
 */
public class Generator {
    public void generator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        // 不覆盖生成文件
        boolean overwrite = false;
        //指向逆向工程配置文件
        File configFile = new File("src/main/resources/gengerator/generatorConfig.xml");
        ConfigurationParser parser = new ConfigurationParser(warnings);
        Configuration config = parser.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
    }
    public static void main(String[] args) throws Exception {
        try {
            Generator generatorSqlmap = new Generator();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
