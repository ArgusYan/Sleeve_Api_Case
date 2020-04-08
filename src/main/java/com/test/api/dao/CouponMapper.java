package com.test.api.dao;

import com.test.api.pojo.Coupon;
import com.test.api.vo.CouponPureVO;

import java.util.Date;
import java.util.List;

public interface CouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    List<CouponPureVO> selectCouponAtActivity(Integer aid);
}