package com.test.api.dao;

import com.test.api.pojo.UserCoupon;
import org.apache.ibatis.annotations.Param;

public interface UserCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    UserCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);

    UserCoupon selectByUserIdAndCouponId(@Param("uid") Integer uid, @Param("cid") Integer cid);

}