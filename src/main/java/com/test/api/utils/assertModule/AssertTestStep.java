package com.test.api.utils.assertModule;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Argus
 * @className AssertTestStep
 * @description: Allure 报告需要记录的数据 待定使用
 * @date 2020/4/5 14:13
 * @Version V1.0
 */
public class AssertTestStep {

    public static void requestAndRespondBody(String URL, String Body,String Respond){
        AssertTestStep.requestBody(URL,Body);
        AssertTestStep.respondBody(Respond);
    }

//    @Attachment("请求报文")
    public static String requestBody(String URL, String body) {
        //格式化json串
        boolean prettyFormat = true; //格式化输出
        JSONObject jsonObject = JSONObject.parseObject(body);
        String str = JSONObject.toJSONString(jsonObject,prettyFormat);

        //报告展现请求报文
        return URL+"\n"+str;
    }

//    @Attachment("响应报文")
    public static String respondBody(String respond) {
        //格式化json串
        boolean prettyFormat = true; //格式化输出
        JSONObject jsonObject = JSONObject.parseObject(respond);
        String str = JSONObject.toJSONString(jsonObject,prettyFormat);

        //报告展现响应报文
        return str;
    }

//    @Attachment("数据库断言结果")
//    public static StringBuffer databaseAssertResult(StringBuffer assertResult){
//        //报告展现数据库断言结果
//        return assertResult;
//    }

//    @Attachment("响应报文断言结果")
//    public static StringBuffer assertRespond(StringBuffer assertResult){
//        //报告展现数据库断言结果
//        return assertResult;
//    }
}