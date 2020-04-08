/**
 * @ClassName CaseUtil
 * @description: TODO
 * @author Argus
 * @Date 2020/3/5 12:22
 * @Version V1.0
 */
package com.test.api.utils;

import com.test.api.model.Case;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CaseUtil {
    /**
     * 保存所有case对象(共享数据)
     */
    public static List<Case> cases;

    static {
        String excelPath = (String) YamlReaderUtil.getAttribute("restExcelPath");
        cases = ExcelUtil.processExcelToList(Case.class, excelPath, 1);
    }

    /**
     * @param path       若路径为 null 或 "" 取默认值
     * @param sheetIndex 若index为-1或
     */
    public static void setCases(String path, Integer sheetIndex) {
        boolean judge_sheetIndex = sheetIndex == null || sheetIndex == -1;
        String _path = (String) YamlReaderUtil.getAttribute("restExcelPath");
        if (StringUtils.isEmpty(path) && judge_sheetIndex) {
            CaseUtil.cases = ExcelUtil.processExcelToList(Case.class, _path, 1);
            return;
        }
        if (StringUtils.isEmpty(path)) {
            CaseUtil.cases = ExcelUtil.processExcelToList(Case.class, _path, sheetIndex);
            return;
        }
        if (judge_sheetIndex) {
            CaseUtil.cases = ExcelUtil.processExcelToList(Case.class, path, 1);
        }

    }

    /**
     * 通过apiId获取Case对象数据
     *
     * @return
     */
    public static Object[][] getCaseObjDatasByApiId(String apiId) {
//        Class<Case> clz = Case.class;
        // 流读取指定apiId的case元素
        List<Case> _cases = CaseUtil.cases.stream().filter(item -> item.getApiId().equals(apiId)).collect(Collectors.toList());
        Object[][] datas = new Object[_cases.size()][1];
        for (int i = 0; i < _cases.size(); i++) {
            Case cs = _cases.get(i);
            datas[i][0] = cs;
        }
        return datas;
    }

    /**
     * @param apiId     指定接口编号
     * @param cellNames 要获取的数据对应的列名数组 可指定多列
     * @return
     */
    public static Object[][] getCaseDatasByApiId(String apiId, String[] cellNames) {
        Class<Case> clz = Case.class;
//        Stream<Case> caseStream = CaseUtil.cases.stream().filter(item -> item.getApiId().equals(apiId));
//        List<Case> _cases = caseStream.collect(Collectors.toList());
        List<Case> _cases = CaseUtil.cases.stream().filter(item -> item.getApiId().equals(apiId)).collect(Collectors.toList());
//        cases.stream().forEach(System.out::println);
        Object[][] datas = new Object[_cases.size()][cellNames.length];
        for (int i = 0; i < _cases.size(); i++) {
            Case cs = _cases.get(i);
            for (int j = 0; j < cellNames.length; j++) {
                // 要反射的方法名
                String methodName = "get" + cellNames[j];
                // 获取到反射的方法对象
                Method method = null;
                String value = null;
                try {
                    method = clz.getMethod(methodName);
                    value = (String) method.invoke(cs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                datas[i][j] = value;
            }
        }
        return datas;
    }


    /**
     * {k:v}
     *
     * @param apiId     指定接口编号
     * @param cellNames 要获取的数据对应的列名数组 可指定多列
     * @return
     */
    public static Object[][] getCaseDatasByApiId2(String apiId, String[] cellNames) {
        Class<Case> clz = Case.class;
//        Stream<Case> caseStream = CaseUtil.cases.stream().filter(item -> item.getApiId().equals(apiId));
//        List<Case> _cases = caseStream.collect(Collectors.toList());
        List<Case> _cases = CaseUtil.cases.stream().filter(item -> item.getApiId().equals(apiId)).collect(Collectors.toList());
//        cases.stream().forEach(System.out::println);
        Object[][] datas = new Object[_cases.size()][cellNames.length];
        for (int i = 0; i < _cases.size(); i++) {
            Case cs = _cases.get(i);
            for (int j = 0; j < cellNames.length; j++) {
                // 要反射的方法名
                String methodName = "get" + cellNames[j];
                // 获取到反射的方法对象
                Method method = null;
                String value = null;
                try {
                    method = clz.getMethod(methodName);
                    value = (String) method.invoke(cs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                map <ApiId,Object[][]>
                datas[i][j] = value;
            }
        }
        return datas;
    }

    /**
     * //todo 待优化
     *
     * @param caseId
     * @param cellNames
     * @return
     */
    public static Object[][] getCaseDatasByCaseId(String caseId, String[] cellNames) {
        Stream<Case> caseStream = CaseUtil.cases.stream().filter(item -> item.getCaseId().equals(caseId));
        List<Case> _cases = caseStream.collect(Collectors.toList());
        Object[][] datas = new Object[_cases.size()][];
//        HashMap<String, String>[][] map = new HashMap[1][1];
        return null;
    }

    public static void main(String[] args) {
//        CaseUtil.setCases("", -1);
//        String[] cellNames = {"ApiId", "Params", "Url"};
//        Object[][] datas = getCaseDatasByApiId("1", cellNames);
//        for (Object[] objects : datas) {
//            for (Object object : objects) {
//                System.out.println(object);
//            }
////            System.out.println(objects);
//        }
        CaseUtil.cases.stream().forEach(System.out::print);
//        Object[][] datas2 = getCaseObjDatasByApiId("1");
//        for (Object[] objects : datas2) {
//            for (Object object : objects) {
//                String param = null;
//                if (object instanceof Case) {
//                   param = ((Case) object).getParams();
//                }
//                System.out.println(param);
//            }
//        }
    }


}
