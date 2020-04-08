/**
 * @ClassName Case
 * @description: TODO
 * @author Argus
 * @Date 2020/3/5 12:22
 * @Version V1.0
 */
package com.test.api.model;

import com.test.api.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.util.Map;

/**
 * 将Case数据读取后保存到Case
 */
@Getter
@Setter
@ToString
public class Case implements Serializable {
    private static final long serialVersionUID = 8619981338102056355L;
    private String caseId;
    private String apiId;
    private String desc;
    private String params;
    private String assertData;
    private String actualData;
    private String result;
    private int rowNum;

    private Map<String,Integer> cellNameCellNumMapping;

    public Integer getCellNumByCellName(String cellName) {
        return cellNameCellNumMapping.get(StringUtil.upperCase(cellName));
    }

}
