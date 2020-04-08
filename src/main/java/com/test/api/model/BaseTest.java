package com.test.api.model;

import com.test.api.common.HttpClientResult;
import com.test.api.core.config.AssertListener;
import com.test.api.dao.OrderMapper;
import com.test.api.dao.UserCouponMapper;
import com.test.api.utils.CaseUtil;
import com.test.api.utils.SqlSessionUtils;
import com.test.api.utils.assertModule.AssertUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Argus
 * @className BaseTest
 * @description: 测试基类准备数据
 * @date 2020/4/4 18:51
 * @Version V1.0
 */

public class BaseTest {
    //column actualData 的column num
    public static Integer actualData;

    public static void testAssert(HashMap<String, String> datas, HttpClientResult httpClientResult){
        int caseRow = Integer.parseInt(datas.get("caseRow"));
        AssertUtil.asserts(datas.get("caseId"),datas.get("url")
                ,datas.get("assertData"),datas.get("params")
                ,httpClientResult,datas.get("desc"),caseRow);
    }

//    public static void testCleanAssert(){
//        AssertUtil.clear();
//    }

    /**
     * 初始化数据 根据apiId
     * @param context 测试上下文 用于传递数据
     */
    public static void envAllSetUp(ITestContext context){
        BaseTest.actualData = CaseUtil.cases.get(0).getCellNumByCellName("actualData");
        context.setAttribute("actualData",actualData);
        DataProviders.apiId = (Integer)context.getAttribute("apiId");
        DataProviders.initDataByApiId();
    }

    /**
     * 初始化数据 根据caseId 过滤出id相等的数据
     * @param context 测试上下文 用于传递数据
     */
    public static void envAllSetUpByCase(ITestContext context){
        BaseTest.actualData = CaseUtil.cases.get(0).getCellNumByCellName("actualData");
        context.setAttribute("actualData",actualData);
        DataProviders.caseId = (Integer)context.getAttribute("caseId");
        DataProviders.initDataByCaseId();
    }


    @SuppressWarnings("unchecked")
    public static HashMap<String, String> getByApiId(ITestContext context,Integer apiId) {
        HashMap<String, String> map = new HashMap<>(0);
        HashMap<String, String>[][] datas = (HashMap<String, String>[][])context.getAttribute("datas");
        for (HashMap<String, String>[] data : datas) {
            for (HashMap<String, String> datum : data) {
                if (datum.get("apiId").equals(String.valueOf(apiId))) {
                    map =datum;
                }
            }
        }
        return map;
    }





    public static void main(String[] args) {
        Calendar now = Calendar.getInstance();
        Date time = now.getTime();
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        System.out.println(sdf1.format(time));
    }
}