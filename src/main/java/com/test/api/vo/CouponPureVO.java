package com.test.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Argus
 * @className CouponPureVO
 * @description: TODO
 * @date 2020/3/25 15:14
 * @Version V1.0
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponPureVO implements Serializable {
    private static final long serialVersionUID = 6632243221569548266L;
    private Long id;
    private String title;
    @JsonProperty("start_time")
    private Date startTime;
    @JsonProperty("end_time")
    private Date endTime;
    private String description;
    @JsonProperty("full_money")
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private String remark;
    @JsonProperty("whole_store")
    private Boolean wholeStore;

}