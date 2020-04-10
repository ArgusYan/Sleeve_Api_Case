package com.test.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.test.api.common.HttpClientResult;
import com.test.api.core.enums.HttpStatusEnum;
import com.test.api.dto.ClientDTO;
import com.test.api.interfaces.InterfaceSpu;
import com.test.api.model.*;
import com.test.api.utils.GenericAndJson;
import com.test.api.utils.ReportUtil;
import com.test.api.utils.assertModule.AssertUtil;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Argus
 * @className SPULatestTest
 * @description: TODO
 * @date 2020/4/5 21:24
 * @Version V1.0
 */
public class SPULatestTest extends BaseTest{
    /**
     * 环境准备
     * 测试数据准备
     * @param context
     */
    @BeforeClass
    public void envSetUp(ITestContext context){
        //设置api编号
        context.setAttribute("apiId",2);
        //数据准备
        BaseTest.envAllSetUp(context);
    }


    /**
     * 测试
     * @param datas
     * @param context
     */
    @Test(testName = "浏览SPU接口测试",dataProvider = "provideByApiId", dataProviderClass = DataProviders.class)
    public void testScanSpu(HashMap<String, String> datas, ITestContext context) {
        switch (Integer.parseInt(datas.get("caseId"))){
            case 4:
                testCaseScanSpu01(datas);
                break;
            case 5:
                testCaseScanSpu02(datas);
                break;
            default:
                ReportUtil.log("案例编号错误!待核查！");
                break;
        }
    }

    /**
     *  分页为1,10的数据
     * @param datas
     */
    public void testCaseScanSpu02(HashMap<String, String> datas) {
        HttpClientResult httpClientResult = this.runClient(datas);
        //断言 响应的数据 的count值是否为10
        AssertUtil.assertEquals(httpClientResult,"count","10");
        BaseTest.testAssert(datas,httpClientResult);
        AssertUtil.assertResCode(httpClientResult, HttpStatusEnum.OK.code());
        AssertUtil.assertCollectEx();
    }

    public void testCaseScanSpu01(HashMap<String, String> datas) {
        HttpClientResult httpClientResult = this.runClient(datas);
        //断言 响应的数据 的count值是否为5
        AssertUtil.assertEquals(httpClientResult,"count","5");
        BaseTest.testAssert(datas,httpClientResult);
        //断言 响应的Code码是否为201
        AssertUtil.assertResCode(httpClientResult,HttpStatusEnum.OK.code());
        AssertUtil.assertCollectEx();
    }

    private HttpClientResult runClient(HashMap<String, String> datas) {
        HttpClientResult httpClientResult = null;
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        Map<String, String> params = GenericAndJson.jsonToObjOrCollection(datas.get("params"), new TypeReference<Map<String, String>>() {
        });
        ClientDTO clientDTO = ClientDTO.builder().url(datas.get("url")).headers(header).params(params).build();
        InterfaceSpu interfaceSpu = new InterfaceSpu();
        interfaceSpu.setClientParams(clientDTO);
        try {
            httpClientResult = interfaceSpu.client(InterfaceSpu.TYPE1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert httpClientResult != null;
        return httpClientResult;
    }


    @AfterClass
    public void after() {
        AssertUtil.clear();
    }
}