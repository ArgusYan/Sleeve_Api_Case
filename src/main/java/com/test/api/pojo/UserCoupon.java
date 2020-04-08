package com.test.api.pojo;

import lombok.*;

import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCoupon {
    private Integer id;

    private Integer userId;

    private Integer couponId;

    private Byte status;

    private Date createTime;

    private Integer orderId;

    private Date updateTime;


}