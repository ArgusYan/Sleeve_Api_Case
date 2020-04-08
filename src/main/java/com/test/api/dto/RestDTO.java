/**
 * @ClassName RestDTO
 * @description: TODO
 * @author Argus
 * @Date 2020/3/6 15:00
 * @Version V1.0
 */
package com.test.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@ToString
@Getter
@Setter
public class RestDTO implements Serializable {
    private static final long serialVersionUID = -6888128890950013088L;
    private String caseId;
    private String apiId;
    private String apiName;
    private String type;
    private String url;
    private String desc;
    private String params;
    //对应case的row
    private Integer caseRow;
    private String assertData;
    private String actualData;
    private String result;


}
