/**
 * @ClassName DataProviders
 * @description: TODO
 * @author Argus
 * @Date 2020/3/6 0:58
 * @Version V1.0
 */
package com.test.api.model;

import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.HashMap;

public class DataProviders {
    public static HashMap<String, String>[][] hashMaps;

    public static Integer caseId;

    public static Integer apiId;

    /*
    *  获取所有的data
    *  数据提供者在方法签名中声明了一个 ITestContext 类型的参数
    *  testng 会将当前的测试上下文设置给它
    *   @Test
    *     public void keyword(ITestContext context){
    *        context.setAttribute("demoString", "Test Passing Value.")
    *    }
    *    @Test
    *    public void keywordPass(ITestContext context){
    *        System.out.println(context.getAttribute("demoString"));
    *    }
    * ITestContext context,parallel = true
    */
    @DataProvider(name = "provideAll")
    public static Object[][] initDataAll(){
        TestProcessor processor = TestProcessor.getInstance();
        processor.initTestData();
        return DataProviders.hashMaps;
    }

    /**
     * 根据apiId获取data
     * testng的 形式参数不能随便添加 否则会造成 test ignored
     * @return
     */
    @DataProvider(name = "provideByApiId")
    public static Object[][] initDataByApiId(){
        TestProcessor processor = TestProcessor.getInstance();
        processor.initTestDataByApiId(apiId);
        return DataProviders.hashMaps;
    }

    /**
     * 根据用例编号去获取data
     * @return
     */
    @DataProvider(name = "provideByCaseId")
    public static Object[][] initDataByCaseId(){
        TestProcessor processor = TestProcessor.getInstance();
        processor.initTestDataByCaseId(caseId);
        return DataProviders.hashMaps;
    }

    public static void main(String[] args) {
        initDataByApiId();
        for (HashMap<String, String>[] hashMap : hashMaps) {
            for (HashMap<String, String> stringStringHashMap : hashMap) {
                Arrays.stream(hashMap).forEach(System.out::println);
            }
        }
    }


}
