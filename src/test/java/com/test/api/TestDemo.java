package com.test.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.test.api.common.HttpClientResult;
import com.test.api.common.HttpClientTool;
import com.test.api.dao.CouponMapper;
import com.test.api.dao.OrderMapper;
import com.test.api.dao.UserCouponMapper;
import com.test.api.model.BaseTest;
import com.test.api.model.DataProviders;
import com.test.api.pojo.UserCoupon;
import com.test.api.utils.GenericAndJson;
import com.test.api.utils.ReportUtil;
import com.test.api.utils.SqlSessionUtils;
import com.test.api.utils.assertModule.AssertUtil;
import com.test.api.vo.CouponPureVO;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Argus
 * @className TestDemo
 * @description: TODO
 * @date 2020/4/7 15:29
 * @Version V1.0
 */
public class TestDemo {
    /**
     * 环境准备
     * 测试数据准备
     */
//    @BeforeClass
//    public void envSetUp(ITestContext context){
//        //设置api编号
//        context.setAttribute("caseId",6);
//        //数据准备
//        BaseTest.envAllSetUpByCase(context);
//        context.setAttribute("datas", DataProviders.hashMaps);
//    }
//
//    /**
//     *  浏览活动的优惠券
//     * @param context
//     */
//    @Test(testName = "查看活动优惠券",priority = 4)
//    public void test1(ITestContext context) {
//        HashMap<String, String> data = BaseTest.getByApiId(context, 3);
//        String url = data.get("url");
//        HttpClientResult httpClientResult = null;
//        try {
//            httpClientResult = HttpClientTool.doGetWithoutHeadersAndParams(url);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assert httpClientResult != null;
//        JSONObject jsonObj = JSON.parseObject(httpClientResult.getContent());
//        String couponsJsonStr = JSON.toJSONString(jsonObj.get("coupons"));
//        //转换json 拿到后端传递的值查看优惠券
//        List<CouponPureVO> couponPureVOList = GenericAndJson.jsonToObjOrCollection(couponsJsonStr, new TypeReference<List<CouponPureVO>>() {});
//        assert couponPureVOList != null;
//        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
//        CouponMapper couponMapper = sqlSession.getMapper(CouponMapper.class);
//        List<CouponPureVO> couponPureDb = couponMapper.selectCouponAtActivity(2);
//        // 断言 后台传递本次活动的coupon数量 与 数据库的数据 是否一致
//        Assert.assertEquals(couponPureVOList.size(),couponPureDb.size());
//        //获取id为7的优惠券
//        CouponPureVO coupon = couponPureVOList.stream().filter(vo -> vo.getId() == 7).findAny().orElse(null);
//        Assert.assertNotNull(coupon);
//        context.setAttribute("coupon",coupon);
//        BaseTest.testAssert(data,httpClientResult);
//        AssertUtil.assertCollectEx();
//    }
//
//    @Test(priority = 3)
//    public void test2() {
//        HttpClientResult httpClientResult = null;
//        try {
//            Map<String,String> headers = new HashMap<>(0);
//
//            headers.put("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjQyLCJzY29wZSI6OCwiZXhwIjoxNTg2NzYyOTU3LCJpYXQiOjE1ODU4OTg5NTd9.MhLNh3sbcFUXrW0D6QG_g1KWg2P96V5KRwn5G-XwOSM");
//
//            String url = "http://localhost:8088/v1/coupon/collect/7";
//
//            httpClientResult = HttpClientTool.doPostWithHeadersWithoutParams(url, headers);
//            ReportUtil.log(httpClientResult.getContent());
//        } catch (Exception e) {
//            ReportUtil.log(e.getMessage());
//            e.printStackTrace();
//        }
//        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
//        UserCouponMapper userCouponMapper = sqlSession.getMapper(UserCouponMapper.class);
//        UserCoupon userCoupon = userCouponMapper.selectByUserIdAndCouponId(42, 7);
//        ReportUtil.log(userCoupon.toString());
//    }


    @Test(priority = 1)
    public static void test3() {
        try {
            Thread.sleep(3600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserCouponMapper userCouponMapper = sqlSession.getMapper(UserCouponMapper.class);
        UserCoupon userCoupon = userCouponMapper.selectByUserIdAndCouponId(42, 7);
        ReportUtil.log(userCoupon.toString());
    }

    @Test(priority = 3)
    public static void test4() {
        try {
            Thread.sleep(3600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        UserCouponMapper userCouponMapper = sqlSession.getMapper(UserCouponMapper.class);
        UserCoupon userCoupon = userCouponMapper.selectByUserIdAndCouponId(42, 7);
        ReportUtil.log(userCoupon.toString());
    }

}