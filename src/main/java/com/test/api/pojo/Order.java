package com.test.api.pojo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;

    private String orderNo;

    private Integer userId;

    private BigDecimal totalPrice;

    private Integer totalCount;

    private Date createTime;

    private Date deleteTime;

    private Date updateTime;

    private String snapImg;

    private String snapTitle;

    private String snapItems;

    private String snapAddress;

    private String prepayId;

    private BigDecimal finalTotalPrice;

    private Byte status;

    private Date expiredTime;

    private Date placedTime;


}