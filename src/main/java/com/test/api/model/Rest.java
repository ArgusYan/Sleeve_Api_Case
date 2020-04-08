/**
 * @ClassName Rest
 * @description: TODO
 * @author Argus
 * @Date 2020/3/5 21:35
 * @Version V1.0
 */
package com.test.api.model;

import com.test.api.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Rest implements Serializable {
    private static final long serialVersionUID = 4274954154523691171L;
    private String apiId;
    private String apiName;
    private String type;
    private String url;
    private int rowNum;
    private Map<String,Integer> cellNameCellNumMapping;
    private List<Case> cases;


    public String getUrlByApiId(String apiId) {
        if (this.getApiId().equals(apiId)) {
            return this.getUrl();
        }
        return "";
    }

    public String getTypeByApiId(String apiId) {
        if (this.getType().equals(type)) {
            return this.getUrl();
        }
        return "";
    }

    public Integer getCellNumByCellName(String cellName) {
        return cellNameCellNumMapping.get(StringUtil.upperCase(cellName));
    }



}
