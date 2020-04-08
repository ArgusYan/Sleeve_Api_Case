package com.test.api.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Argus
 * @className GetSqlSessionFactory
 * @description: TODO
 * @date 2020/4/6 13:29
 * @Version V1.0
 */
public class SqlSessionUtils {
    private static SqlSession sqlSession = null;

    /**
     * 获取单例的sqlSession
     * @return
     */
    public static SqlSession getSqlSession() {
        //如果sqlSessionFactory没有被创建就读取全局配置文件，假如已经被创建过了，就使用已经存在的sqlsessionfactory。
        if (sqlSession == null) {
            //1.从 XML 中构建 SqlSessionFactory
            String resource = "mybatis-config.xml";
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
        }
        return sqlSession;
    }

    /**
     * 获取多例的sqlSession
     * @return
     */
    public static SqlSession getMultipleSqlSession() {

        //如果sqlSessionFactory没有被创建就读取全局配置文件，假如已经被创建过了，就使用已经存在的sqlsessionfactory。
        //1.从 XML 中构建 SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        return sqlSessionFactory.openSession();
    }
}