package com.test.api.utils.assertModule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.test.api.common.HttpClientResult;
import com.test.api.core.config.TestngRetry;
import com.test.api.model.BaseTest;
import com.test.api.model.TestProcessor;
import com.test.api.utils.*;
import com.test.api.vo.CouponPureVO;
import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.checkerframework.checker.units.qual.K;
import org.testng.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssertUtil {
//    static final ThreadLocal<List<String>> localEx = new ThreadLocal<>();
public static final List<AssertionError> localEx = new ArrayList<>(0);
    public static void setLocalEx(AssertionError exceptionInfo){
//        List<String> exList = new ArrayList<>(0);
        AssertUtil.localEx.add(exceptionInfo);
//        AssertUtil.localEx.set(exList);
    }

    public static List<AssertionError> getList() {
        return AssertUtil.localEx;
    }

    public static void clear() {
        AssertUtil.localEx.clear();
    }

    public static void contains(String source, String search) {
        Assert.assertTrue(source.contains(search),
                String.format("期待'%s'包含'%s'，实际为不包含.", source, search));
    }

    public static void notContains(String source, String search) {
        Assert.assertFalse(source.contains(search),
                String.format("期待'%s'不包含'%s'，实际为包含.", source, search));
    }

    public static void equals(String source, String search) {
        Assert.assertTrue(source.equals(search),
                String.format("期待'%s'相等于'%s'，实际为不相等.", source, search));
    }

    public static void notEquals(String source, String search) {
        Assert.assertFalse(source.equals(search),
                String.format("期待'%s'不相等于'%s'，实际为相等于.", source, search));
    }


    /**
     * @param url
     * @param expected
     * @param req
     * @param res
     */
    public static void asserts2(String caseId, String url, String expected, String req, String res, String desc) {


        AssertTestStep.requestAndRespondBody(url, req, res);


        //格式化json串
        boolean prettyFormat = true; //格式化输出
        JSONObject jsonObject = JSONObject.parseObject(res);

        res = JSONObject.toJSONString(jsonObject, prettyFormat);

        ReportUtil.log("案例编号：" + caseId);
        ReportUtil.log("接口描述：" + desc);
        ReportUtil.log("响应报文：" + res);
        //断言（包含响应报文断言和数据库断言）
        AssertUtil.respondAsserts(desc, res, expected);
    }


    public static void respondAsserts(String desc, String res, String expected) {

        StringBuffer stringBufferResult;
        boolean assertFlag = true;
        try {
            Assert.assertEquals(res, expected);
        } catch (AssertionError e) {
            ReportUtil.log("-----响应报文断言False!请查看断言结果！-----");
            ReportUtil.log(String.valueOf(e));
            //响应报文断言
            stringBufferResult = assertInfo(desc, false, expected, res);
            ReportUtil.log("-----响应报文断言False!请查看断言结果！-----");
        }
        //测试报告展现 响应报文断言结果（无论成功还是失败）
        stringBufferResult = assertInfo(desc, true, expected, res);
        //todo 断言不通过，flag标志赋值为false
//        AssertTestStep.assertRespond(stringBufferResult);
    }


    public static void asserts(String caseId, String url, String expected, String req, HttpClientResult res, String desc, Integer caseRow) {
        ReportUtil.log("案例编号：" + caseId);
        ReportUtil.log("接口描述：" + desc);
        ReportUtil.log("接口URL：" + url);
        if (StringUtils.isNotBlank(req)) {
            ReportUtil.log("请求Data：" + req);
        }
        Assert.assertNotNull(res);
        String resContent = res.getContent();
        String resFormat = JsonFormatUtil.jsonFormat(resContent);
        System.out.println(resFormat);
        ReportUtil.log("响应Code：" + res.getCode());
        ReportUtil.log("预期Data：" + expected);
        ReportUtil.log("响应Data：" + resFormat);
        StringBuffer stringBufferResult = null;
        boolean assertFlag = true;

        try {
            Assert.assertEquals(resFormat, expected);
        } catch (AssertionError e) {
            assertFlag = false;
            ReportUtil.log("-----响应报文验证False!请查看断言结果！-----");
            ReportUtil.log(String.valueOf(e));
            //响应报文断言

            stringBufferResult = assertInfo(desc, false, expected, resFormat);
            ReportUtil.log("-----响应报文验证False!请查看断言结果！-----");
            collectResults(caseRow, stringBufferResult);
            ExcelUtil.writeBackData(TestProcessor.excelPath, 1, BaseTest.actualData, caseRow, resFormat);
            AssertUtil.setLocalEx(e);
            throw new AssertionError(e);
        }
        // 验证数据成功
        if (assertFlag) {
            //测试报告展现 响应报文断言结果（无论成功还是失败）
            stringBufferResult = assertInfo(desc, true, expected, res.getContent());
            collectResults(caseRow, stringBufferResult);
        }
        ReportUtil.log(stringBufferResult.toString());
    }

    private static void collectResults(Integer caseRow, StringBuffer stringBufferResult) {
        Integer resultIndex = CaseUtil.cases.get(0).getCellNumByCellName("result");
        ExcelUtil.writeBackData(TestProcessor.excelPath, 1, resultIndex, caseRow, stringBufferResult.toString());

    }

    public static StringBuffer assertInfo(String desc, Boolean assertFlag, String expected, String actual) {
        StringBuffer stringBufferResult = new StringBuffer();
        if (assertFlag) {
            stringBufferResult.append("接口：【\n" + desc + "->验证为:\n" + assertFlag + String.format("，期待\n'%s'\n等于\n'%s'，实际相等！】\n", expected, actual));
        } else {
            stringBufferResult.append("接口：【\n" + desc + "->验证为:\n" + assertFlag + String.format("，期待\n'%s'\n等于\n'%s'，实际不相等！】\n", expected, actual));
        }
        return stringBufferResult;
    }

    /**
     *
     * @param res
     * @param key
     * @param expected
     */
    public static void assertEquals(HttpClientResult res,String key,String expected) {
        Assert.assertNotNull(res);
        String value = null;
        if (res.getContent().substring(0,1).equals("{")) {
            JSONObject jsonObj = JSON.parseObject(res.getContent());
            value = String.valueOf(jsonObj.get(key));
        }
        try {
            Assert.assertEquals(value, expected);
        }catch (AssertionError e) {
            ReportUtil.log("=====响应数据Equals:断言失败=====");
            ReportUtil.log(String.valueOf(e));
            ReportUtil.log("=====响应数据Equals:断言失败=====");
//            throw new AssertionError(e);
//            AssertUtil.setLocalEx(String.valueOf(e));
            AssertUtil.setLocalEx(e);
        }
    }


    public static<T> void assertEqualsJsonArray(HttpClientResult res,String fieldName,String expected,TypeReference<T> tr) throws NoSuchFieldException, IllegalAccessException {
        Assert.assertNotNull(res);
        String value = null;
        if (res.getContent().substring(0,1).equals("[")) {
            JSONArray jsonArray = JSON.parseArray(res.getContent());
            T t = GenericAndJson.jsonToObjOrCollection(res.getContent(), tr);
            Field field = t.getClass().getDeclaredField(fieldName);
            Object o = field.get(t);
            value = String.valueOf(o);
        }

        try {
            Assert.assertEquals(value, expected);
        }catch (AssertionError e) {
            ReportUtil.log("=====响应数据Equals:断言失败=====");
            ReportUtil.log(String.valueOf(e));
            ReportUtil.log("=====响应数据Equals:断言失败=====");
//            throw new AssertionError(e);
//            AssertUtil.setLocalEx(String.valueOf(e));
            AssertUtil.setLocalEx(e);
        }
    }


//    public static<T,K> T assertListContains(HttpClientResult res, Class<T> clazz, String fieldName, K fieldType,K expected){
//        Assert.assertNotNull(res);
//        String json = res.getContent();
//        List<T> ts = GenericAndJson.jsonToObjOrCollection(json, new TypeReference<List<T>>() {});
//        T t = null;
//        try {
//            assert ts != null;
//            t = ts.stream().filter(ele -> {
//                K invoke = null;
//                try {
//                    String firstLetter = StringUtil.upperCase(fieldName);
//                    String getter = "get" + firstLetter;
//                    Method method = ele.getClass().getMethod(getter, fieldType.getClass());
//                    invoke = (K) method.invoke(ele);
//                } catch (Exception e) {
//                    ReportUtil.log(e.getMessage());
//                }
//                if (invoke != null) {
//                    Assert.assertEquals(invoke, expected);
////                    invoke.equals(expected);
//                    return true;
//                }
//                return false;
//            }).findAny().orElse(null);
//
//        }catch (AssertionError e) {
//            ReportUtil.log("=====响应数据Contains:断言失败=====");
//            ReportUtil.log(String.valueOf(e));
//            ReportUtil.log("=====响应数据Contains:断言失败=====");
//            AssertUtil.setLocalEx(e);
//        }
//        return t;
//    }

    public static void assertResCode(HttpClientResult res,Integer expected) {
        Assert.assertNotNull(res);
        try {
            Assert.assertEquals(String.valueOf(res.getCode()), String.valueOf(expected));
        }catch (AssertionError e) {
            ReportUtil.log("=====响应Code码断言失败=====");
            ReportUtil.log(String.valueOf(e));
            ReportUtil.log("=====响应Code码断言失败=====");
//            throw new AssertionError(e);
            AssertUtil.setLocalEx(e);
        }
    }


    public static void assertCollectEx() {
        ReportUtil.log("=====>当前接口AssertionError数量:"+ AssertUtil.getList().size());
        if (AssertUtil.getList().size()>0){
            AssertUtil.getList().forEach(e -> ReportUtil.log("======>当前接口AssertionError:{"+e.getMessage()+"}<=====当前接口AssertionError!\n"));
            throw new AssertionError(AssertUtil.getList().get(0));
        }
    }
}
