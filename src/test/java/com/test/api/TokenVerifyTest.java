/**
 * @ClassName Test
 * @description: TODO
 * @author Argus
 * @Date 2020/3/6 1:01
 * @Version V1.0
 */
package com.test.api;

import com.test.api.common.HttpClientResult;
import com.test.api.common.HttpClientTool;
import com.test.api.core.enums.HttpStatusEnum;
import com.test.api.model.BaseTest;
import com.test.api.model.DataProviders;
import com.test.api.model.TestProcessor;
import com.test.api.utils.*;
import com.test.api.utils.assertModule.AssertUtil;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TokenVerifyTest extends BaseTest{
    /**
     * 环境准备
     * 测试数据准备
     * @param context 测试上下文
     */
    @BeforeClass
    public void envSetUp(ITestContext context){
        //设置api编号
        context.setAttribute("apiId",1);
        //数据准备
        BaseTest.envAllSetUp(context);
    }

    @Test(testName = "登录Token校验接口测试",dataProvider = "provideByApiId", dataProviderClass = DataProviders.class)
    public void testLoginToken(HashMap<String, String> datas,ITestContext context) {
       switch (Integer.parseInt(datas.get("caseId"))){
           case 1:
               testCaseWithToken01(datas);
               break;
           case 2:
               testCaseWithoutToken02(datas);
               break;
           case 3:
               testCaseWithExpiredToken03(datas);
               break;

           default:
               ReportUtil.log("案例编号错误!待核查！");
               break;
       }
    }

    /**
     *
     * @param datas 参数化数据
     */
    public void testCaseWithToken01(HashMap<String, String> datas) {
        String url = datas.get("url");
        Map<String, String> headers = new HashMap<>();
        headers.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjQyLCJzY29wZSI6OCwiZXhwIjoxNTg2NzYyOTU3LCJpYXQiOjE1ODU4OTg5NTd9.MhLNh3sbcFUXrW0D6QG_g1KWg2P96V5KRwn5G-XwOSM");
        HttpClientResult httpClientResult = null;
        try {
            httpClientResult = HttpClientTool.doPostWithHeadersWithoutParams(url, headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert httpClientResult != null;
        BaseTest.testAssert(datas,httpClientResult);
        AssertUtil.assertResCode(httpClientResult,HttpStatusEnum.OK.code());
        AssertUtil.assertCollectEx();
    }

    public void testCaseWithoutToken02(HashMap<String, String> datas) {
        String url = datas.get("url");
        HttpClientResult httpClientResult = null;
        try {
            httpClientResult = HttpClientTool.doPostWithoutHeadersParams(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert httpClientResult != null;
        BaseTest.testAssert(datas,httpClientResult);
        AssertUtil.assertResCode(httpClientResult,HttpStatusEnum.OK.code());
        AssertUtil.assertCollectEx();
    }


    public void testCaseWithExpiredToken03(HashMap<String, String> datas) {
        String url = datas.get("url");
        Map<String, String> headers = new HashMap<>();
        headers.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjQyCJGjHGFJfVjGKUzYyOTU3LCJpYXQiOjE1ODU4OTg5NTd9.MhLNh3sbcFUXrW0D6QG_g1KWg2P96V5KRwn5G-XwOSM");
        HttpClientResult httpClientResult = null;
        try {
            httpClientResult = HttpClientTool.doPostWithHeadersWithoutParams(url, headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert httpClientResult != null;
        BaseTest.testAssert(datas,httpClientResult);
        AssertUtil.assertResCode(httpClientResult, HttpStatusEnum.INTERNAL_SERVER_ERROR.code());
        AssertUtil.assertCollectEx();
    }


    @AfterClass
    public void after() {
        AssertUtil.clear();
    }
}