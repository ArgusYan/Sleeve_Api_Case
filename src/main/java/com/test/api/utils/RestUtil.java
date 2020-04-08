/**
 * @ClassName RestUtil
 * @description: TODO
 * @author Argus
 * @Date 2020/3/5 21:33
 * @Version V1.0
 */
package com.test.api.utils;

import com.beust.jcommander.internal.Lists;
import com.test.api.model.Case;
import com.test.api.model.Rest;

import java.util.List;
import java.util.stream.Collectors;

public class RestUtil {
    public static List<String> apiIds = Lists.newArrayList();

    public static List<Rest> rests = Lists.newArrayList();

    static {
        String excelPath = (String)YamlReaderUtil.getAttribute("restExcelPath");
        rests = ExcelUtil.processExcelToList(Rest.class, excelPath, 0);
        if (rests!=null && rests.size() > 0) {
            for (Rest rest : rests) {
//                System.out.println(rest);
                apiIds.add(rest.getApiId());
//                System.out.println(apiIds);
                List<Case> cases = CaseUtil.cases.stream().filter(item -> item.getApiId().equals(rest.getApiId())).collect(Collectors.toList());
//                System.out.println(cases);
                // 将符合条件的cases集合放到rest对象里面
                rest.setCases(cases);
            }
        }
    }


    public static String getUrlByApiId(String apiId) {
        for (Rest rest : rests) {
            if (rest.getApiId().equals(apiId)) {
                return rest.getUrl();
            }
        }
        return "";
    }


    public static void main(String[] args) {
        List<Rest> rests= RestUtil.rests;
        rests.forEach(s -> {System.out.println("Rest Value=>"+s.getCases());});
    }
}
