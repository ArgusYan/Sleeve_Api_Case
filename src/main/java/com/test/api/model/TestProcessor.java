/**
 * @ClassName TestCenter
 * @description: TODO
 * @author Argus
 * @Date 2020/3/5 22:14
 * @Version V1.0
 */
package com.test.api.model;

import com.beust.jcommander.internal.Lists;
import com.test.api.dto.RestDTO;
import com.test.api.utils.*;
import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 主要处理
 */
public class TestProcessor {

    /**
     * 内部类
     */
    private static class TestProcessorHolder {
        private static TestProcessor instance = new TestProcessor();
    }

    /**
     * 提供单例
     *
     * @return
     */
    public static TestProcessor getInstance() {
        return TestProcessorHolder.instance;
    }

    /**
     * 保存了所有的apiId
     */
    public static List<String> apiIds = Lists.newArrayList();
    /**
     * 保存所有Rest测试对象 里面包含了对应的Case
     */
    public static List<Rest> rests = Lists.newArrayList();


    public static List<RestDTO> restDTOS;

    public static String excelPath;

    private TestProcessor() {
        excelPath = (String) YamlReaderUtil.getAttribute("restExcelPath");
        TestProcessor.rests = ExcelUtil.processExcelToList(Rest.class, excelPath, 0);
        if (rests != null && rests.size() > 0) {
            for (Rest rest : rests) {
                apiIds.add(rest.getApiId());
                // 通过apiId关联Case集合和每个接口
                List<Case> cases = CaseUtil.cases.stream().filter(item -> item.getApiId().equals(rest.getApiId())).collect(Collectors.toList());
//                System.out.println(cases);
                // 将符合条件的cases集合放到rest对象里面
                rest.setCases(cases);
            }
        }
        TestProcessor.restDTOS = _initDTOs();
    }

    /********************************************************
     *  初始化
     *  //todo 建议通过读取配置的方式 去要转换列
     * ******************************************************
     */
    public void initTestData() {
        // 根据字段名去获取对应的二维数组
        HashMap<String,String>[][] hashMaps = getValuesOfDyadicArray(restDTOS,new String[]{"CaseId","apiId", "aPiName", "url", "type","params", "caseRow","Desc","AssertData"});
        // 给数据提供者类初始化excel转换后的Map二维数组类
        DataProviders.hashMaps = hashMaps;
    }


    public void initTestDataByApiId(Integer apiId) {
        List<RestDTO> restDTOInApiId = restDTOS.stream()
                .filter(restDTO -> restDTO.getApiId().equals(apiId.toString()))
                .collect(Collectors.toList());
        // 根据字段名去获取对应的二维数组
        HashMap<String, String>[][] hashMaps = getValuesOfDyadicArray(restDTOInApiId, new String[]{"CaseId", "apiId", "aPiName", "url", "type", "params", "caseRow","Desc","AssertData"});
        // 给数据提供者类初始化excel转换后的Map二维数组类
        DataProviders.hashMaps = hashMaps;
    }

    public void initTestDataByCaseId(Integer caseId) {
        List<RestDTO> restDTOInApiId = restDTOS.stream()
                .filter(restDTO -> restDTO.getCaseId().equals(caseId.toString()))
                .collect(Collectors.toList());
        // 根据字段名去获取对应的二维数组
        HashMap<String, String>[][] hashMaps = getValuesOfDyadicArray(restDTOInApiId, new String[]{"CaseId", "apiId", "aPiName", "url", "type", "params", "caseRow","Desc","AssertData"});
        // 给数据提供者类初始化excel转换后的Map二维数组类
        DataProviders.hashMaps = hashMaps;
    }

    public static void main(String[] args) {
        TestProcessor processor = TestProcessor.getInstance();
        processor.initTestDataByApiId(1);
        TestProcessor.restDTOS.stream().forEach(System.out::println);

    }

    /**
     * dozermapper 也可以支持深拷贝
     * @return
     */
    public static List<RestDTO> _initDTOs() {
        List<RestDTO> restDTOs = Lists.newArrayList();
        try {
            for (Rest rest : TestProcessor.rests) {
                // 深拷贝
                Rest rest_cp = (Rest) BeanUtil.clone(rest);
                List<Case> cases = rest.getCases();
                for (Case _case : cases) {
                    RestDTO restDTO = new RestDTO();
                    Case case_cp = (Case) BeanUtil.clone(_case);
                    BeanUtils.copyProperties(restDTO, rest_cp);
                    restDTO.setCaseRow(case_cp.getRowNum());
                    BeanUtils.copyProperties(restDTO, case_cp);
                    restDTOs.add(restDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restDTOs;
    }


    /**
     * 借助BeanMap 转换Map<String, Object>
     * @param t
     * @param <T>
     * @return
     */
    public <T> Map<String, Object> getValuesAllOfObjMap(T t) {
        // 1个对象转换成map
        Map<String, Object> mapAlter = MapToolsUtil.objectToHashMap(t);
        return mapAlter;
    }

    /**
     * 拿到部分的columnNames对应的Map<String, Object>
     *
     * @param t
     * @param columnNames
     * @param <T>
     * @return
     */
    public <T> Map<String, Object> getValuesSomeOfObjMapStringWithObject(T t, String[] columnNames) {
        Map<String, Object> mapAlter = this.getValuesAllOfObjMap(t);
        //转换
        mapAlter = MapToolsUtil.parseMapForFilterOptional(mapAlter, columnNames);
        return mapAlter;
    }

    /**
     * 拿到部分的columnNames对应的Map<String, String>
     *
     * @param t
     * @param columnNames
     * @param <T>
     * @return
     */
    public <T> Map<String, String> getValuesSomeOfObjMapWith2String(T t, String[] columnNames) {
        // 获取获取columnName列名 对应的map
        Map<String, Object> mapAlter = this.getValuesAllOfObjMap(t);
        //转换
        mapAlter = MapToolsUtil.parseMapForFilterOptional(mapAlter, columnNames);
        // 转换Map<String, String>
        Map<String, String> _map = MapToolsUtil.transformMapStringWithObj(mapAlter);
        return _map;
    }

    /**
     * Object 属性名与列表表头名映射
     * 拿到全部的columnNames对应的Map<String, String>
     *
     * @param <T>
     * @return
     */
    public <T> Map<String, String> getValuesAllOfObjMapWith2String(T t) {
        Map<String, Object> mapAlter = this.getValuesAllOfObjMap(t);
        // 转换Map<String, String>
        Map<String, String> _map = MapToolsUtil.transformMapStringWithObj(mapAlter);
        return _map;
    }

    /**
     * 返回一组Map<String, String>集合
     * 且每个Map拿到全部的columnNames对应的信息
     *
     * @param clazzs
     * @param <T>
     * @return
     */
    public <T> List<Map<String, String>> getListValuesAllOfObjMapWith2String(List<T> clazzs) {
        List<Map<String, String>> mapList = Lists.newArrayList();
        for (T clazz : clazzs) {
            Map<String, String> map = this.getValuesAllOfObjMapWith2String(clazz);
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 特殊处理 Map<String, Object> 因为对外的是控制拿列名都是 Map<String, String>
     *{ { 10, 20 }, { 100, 110 }, { 200, 210 } };
     * @param clazzs
     * @param columnNames
     * @param <T>
     * @return
     */
    public <T> HashMap<String,String>[][] getValuesOfDyadicArray(List<T> clazzs, String[] columnNames) {
        if (columnNames == null) return null;

        HashMap<String,String>[][] hashMaps = new HashMap[clazzs.size()][1];
        for (int i = 0; i < clazzs.size(); i++) {
//            // 获取获取columnName列名 对应的map
////            Map<String, String> _map = this.getValuesSomeOfObjMapWith2String(clazzs.get(i), columnNames);
////            datas[i][0] = _map;
            hashMaps[i][0] = (HashMap<String, String>) this.getValuesSomeOfObjMapWith2String(clazzs.get(i), columnNames);
        }
        return hashMaps;
    }



}
