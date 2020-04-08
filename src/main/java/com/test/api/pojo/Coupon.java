package com.test.api.pojo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    private Integer id;

    private String title;

    private Date startTime;

    private Date endTime;

    private String description;

    private BigDecimal fullMoney;

    private BigDecimal minus;

    private BigDecimal rate;

    private Short type;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private Integer valitiy;

    private Integer activityId;

    private String remark;

    private Byte wholeStore;


}