package com.test.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.test.api.common.HttpClientResult;
import com.test.api.common.HttpClientTool;
import com.test.api.core.enums.HttpStatusEnum;
import com.test.api.dao.CouponMapper;
import com.test.api.dao.OrderMapper;
import com.test.api.dao.UserCouponMapper;
import com.test.api.model.BaseTest;
import com.test.api.model.DataProviders;
import com.test.api.pojo.Order;
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
 * @className CouponCollectTest
 * @description: 多接口 关联下单测试
 * @date 2020/4/5 22:32
 * @Version V1.0
 */
public class OrderTest {


    /**
     * 环境准备
     * 测试数据准备
     *
     * @param context
     */
    @BeforeClass
    public void envSetUp(ITestContext context) {
        //设置api编号
        context.setAttribute("caseId", 6);
        //数据准备
        BaseTest.envAllSetUpByCase(context);
        context.setAttribute("datas", DataProviders.hashMaps);
    }

    /**
     * 浏览活动的优惠券
     *
     * @param context
     */
    @Test(testName = "查看活动优惠券", priority = 2)
    public void testCaseScanCoupon(ITestContext context) {
        HashMap<String, String> data = BaseTest.getByApiId(context, 3);
        String url = data.get("url");
        HttpClientResult httpClientResult = null;
        try {
            httpClientResult = new HttpClientTool().doGetWithoutHeadersAndParams(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert httpClientResult != null;
        JSONObject jsonObj = JSON.parseObject(httpClientResult.getContent());
        String couponsJsonStr = JSON.toJSONString(jsonObj.get("coupons"));
        //转换json 拿到后端传递的值查看优惠券
        List<CouponPureVO> couponPureVOList = GenericAndJson.jsonToObjOrCollection(couponsJsonStr, new TypeReference<List<CouponPureVO>>() {
        });
        assert couponPureVOList != null;
        SqlSession sqlSession = SqlSessionUtils.getMultipleSqlSession();
        CouponMapper couponMapper = sqlSession.getMapper(CouponMapper.class);
        List<CouponPureVO> couponPureDb = couponMapper.selectCouponAtActivity(2);
        // 断言 后台传递本次活动的coupon数量 与 数据库的数据 是否一致
        Assert.assertEquals(couponPureVOList.size(), couponPureDb.size());
        //获取id为7的优惠券
        CouponPureVO coupon = couponPureVOList.stream().filter(vo -> vo.getId() == 7).findAny().orElse(null);
        Assert.assertNotNull(coupon);
        context.setAttribute("coupon", coupon);
        BaseTest.testAssert(data, httpClientResult);
        AssertUtil.assertCollectEx();
        sqlSession.close();
    }

    /**
     * 浏览自己的优惠券
     *
     * @param context
     */
    @Test(testName = "获取优惠券", priority = 1, dependsOnMethods = "testCaseScanCoupon")
    public void testCaseCollectCoupon(ITestContext context) {
        HashMap<String, String> data = BaseTest.getByApiId(context, 4);
        CouponPureVO coupon = (CouponPureVO) context.getAttribute("coupon");
        Map<String, String> headers = new HashMap<>(0);
        headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjQyLCJzY29wZSI6OCwiZXhwIjoxNTg2NzYyOTU3LCJpYXQiOjE1ODU4OTg5NTd9.MhLNh3sbcFUXrW0D6QG_g1KWg2P96V5KRwn5G-XwOSM");
        HttpClientResult httpClientResult = null;
        try {
            httpClientResult = new HttpClientTool().doPostWithHeadersWithoutParams(data.get("url") + data.get("params").trim(), headers);
            ReportUtil.log(httpClientResult.getContent());
        } catch (Exception e) {
            ReportUtil.log(e.getMessage());
            e.printStackTrace();
        }
        SqlSession sqlSession = SqlSessionUtils.getMultipleSqlSession();
        UserCouponMapper userCouponMapper = sqlSession.getMapper(UserCouponMapper.class);
        int cid = coupon.getId().intValue();
        UserCoupon userCoupon = userCouponMapper.selectByUserIdAndCouponId(42, cid);
        ReportUtil.log(userCoupon.toString());
        Assert.assertNotNull(userCoupon);
        context.setAttribute("userCoupon", userCoupon);
        BaseTest.testAssert(data, httpClientResult);
        AssertUtil.assertResCode(httpClientResult, HttpStatusEnum.CREATED.code());
        AssertUtil.assertEquals(httpClientResult, "message", "ok");
        AssertUtil.assertCollectEx();
        sqlSession.close();
    }

    /**
     * 浏览自己的优惠券
     *
     * @param context
     */
    @Test(testName = "查看我的优惠券", priority = 3, dependsOnMethods = {"testCaseCollectCoupon"})
    public void testCaseScanMyCoupon(ITestContext context) {
        HashMap<String, String> data = BaseTest.getByApiId(context, 5);
//        UserCoupon userCoupon = (UserCoupon)context.getAttribute("userCoupon");
        Map<String, String> headers = new HashMap<>(0);
        headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjQyLCJzY29wZSI6OCwiZXhwIjoxNTg2NzYyOTU3LCJpYXQiOjE1ODU4OTg5NTd9.MhLNh3sbcFUXrW0D6QG_g1KWg2P96V5KRwn5G-XwOSM");
        HttpClientResult httpClientResult = null;
        try {
            System.out.println(data.get("url") + data.get("params"));
            httpClientResult = new HttpClientTool().doGetWithHeadersWithoutParams(data.get("url") + data.get("params"), headers);
        } catch (Exception e) {
            ReportUtil.log("=====>异常为：" + e.getMessage());
            e.printStackTrace();
        }
        String json = httpClientResult.getContent();
        List<CouponPureVO> couponPureVOList = GenericAndJson.jsonToObjOrCollection(json, new TypeReference<List<CouponPureVO>>() {
        });
        long resultNum = couponPureVOList.stream().filter(vo -> vo.getId() == 7L && vo.getTitle().equals("满1000减230券")).count();
        Assert.assertEquals(resultNum, 1);
        BaseTest.testAssert(data, httpClientResult);
        AssertUtil.assertResCode(httpClientResult, HttpStatusEnum.OK.code());
        AssertUtil.assertCollectEx();
    }

    /**
     * 下单
     *
     * @param context
     */
    @Test(testName = "下单",priority = 4,dependsOnMethods = {"testCaseScanMyCoupon"})
    public void testCaseGetOrder(ITestContext context) {
        HashMap<String, String> data = BaseTest.getByApiId(context, 6);
        Map<String, String> headers = new HashMap<>(0);
        headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjQyLCJzY29wZSI6OCwiZXhwIjoxNTg2NzYyOTU3LCJpYXQiOjE1ODU4OTg5NTd9.MhLNh3sbcFUXrW0D6QG_g1KWg2P96V5KRwn5G-XwOSM");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        HttpClientResult httpClientResult = null;
        try {
//            OrderDTO orderDTO = GenericAndJson.jsonToObjOrCollection(data.get("params"), new TypeReference<OrderDTO>() {
//            });
//            String json = GenericAndJson.ObjToJson(orderDTO);
            httpClientResult = new HttpClientTool().doPostWithJsonWithHeaders(data.get("url").trim(), headers, data.get("params"));
        } catch (Exception e) {
            ReportUtil.log(e.getMessage());
            e.printStackTrace();
        }
        SqlSession sqlSession = SqlSessionUtils.getMultipleSqlSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        ReportUtil.log(httpClientResult.getContent());
        JSONObject jsonObject = JSONObject.parseObject(httpClientResult.getContent());
        Integer id = (Integer) jsonObject.get("id");
        Order order = orderMapper.selectByPrimaryKey(id);
        Assert.assertNotNull(order);
        // 因为数据库的订单生成了 将其记录下来
        AssertUtil.assertResCode(httpClientResult, HttpStatusEnum.CREATED.code());
        AssertUtil.assertCollectEx();
        sqlSession.close();
    }

}