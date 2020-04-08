package com.test.api.utils;

import com.beust.jcommander.internal.Lists;
import com.test.api.model.Case;
import com.test.api.model.Rest;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

//    /**
//     * 获取excel表所有sheet数据
//     *
//     * @param clz
//     * @param path
//     * @return
//     */
//    public static <T> List<T> readExcel(Class<T> clz, String path) {
//        System.out.println(path);
//        if (null == path || "".equals(path)) {
//            return null;
//        }
//        InputStream is;
//        Workbook xssfWorkbook;
//        try {
//            is = new FileInputStream(path);
//            if (path.endsWith(".xls")) {
//                xssfWorkbook = new HSSFWorkbook(is);
//            } else {
//                xssfWorkbook = new XSSFWorkbook(is);
//            }
//            is.close();
//            int sheetNumber = xssfWorkbook.getNumberOfSheets();
//            List<T> allData = new ArrayList<T>();
//            for (int i = 0; i < sheetNumber; i++) {
//                allData.addAll(transToObject(clz, xssfWorkbook,
//                        xssfWorkbook.getSheetName(i)));
//            }
//            return allData;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("转换excel文件失败：" + e.getMessage());
//        }
//    }
//
//    /**
//     * 获取excel表指定sheet表数据
//     *
//     * @param clz
//     * @param path
//     * @param sheetName
//     * @return
//     */
//    public static <T> List<T> readExcel(Class<T> clz, String path,
//                                        String sheetName) {
//        if (null == path || "".equals(path)) {
//            return null;
//        }
//        InputStream is;
//        Workbook xssfWorkbook;
//        try {
//            is = new FileInputStream(path);
//            if (path.endsWith(".xls")) {
//                xssfWorkbook = new HSSFWorkbook(is);
//            } else {
//                xssfWorkbook = new XSSFWorkbook(is);
//            }
//            is.close();
//            return transToObject(clz, xssfWorkbook, sheetName);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("转换excel文件失败：" + e.getMessage());
//        }
//
//    }
//
//    private static <T> List<T> transToObject(Class<T> clz,
//                                             Workbook xssfWorkbook, String sheetName)
//            throws InstantiationException, IllegalAccessException,
//            InvocationTargetException {
//        List<T> list = new ArrayList<T>();
//        Sheet xssfSheet = xssfWorkbook.getSheet(sheetName);
//        Row firstRow = xssfSheet.getRow(0);
//        if (null == firstRow) {
//            return list;
//        }
//        List<Object> heads = getRow(firstRow);
//        //添加sheetName字段，用于封装至bean中，与bean中的字段相匹配。
//        heads.add("sheetName");
//        Map<String, Method> headMethod = getSetMethod(clz, heads);
//        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
//            try {
//                Row xssfRow = xssfSheet.getRow(rowNum);
//                if (xssfRow == null) {
//                    continue;
//                }
//                T t = clz.newInstance();
//                List<Object> data = getRow(xssfRow);
//                //如果发现表数据的列数小于表头的列数，则自动填充为null，最后一位不动，用于添加sheetName数据
//                while (data.size() + 1 < heads.size()) {
//                    data.add("");
//                }
//                data.add(sheetName);
//                setValue(t, data, heads, headMethod);
//                list.add(t);
//            } catch (IllegalArgumentException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return list;
//    }
//
//    private static Map<String, Method> getSetMethod(Class<?> clz,
//                                                    List<Object> heads) {
//        Map<String, Method> map = new HashMap<String, Method>();
//        Method[] methods = clz.getMethods();
//        for (Object head : heads) {
//            // boolean find = false;
//            for (Method method : methods) {
//                if (method.getName().toLowerCase()
//                        .equals("set" + head.toString().toLowerCase())
//                        && method.getParameterTypes().length == 1) {
//                    map.put(head.toString(), method);
//                    // find = true;
//                    break;
//                }
//            }
//            // if (!find) {
//            // map.put(head, null);
//            // }
//        }
//        return map;
//    }
//
//    private static void setValue(Object obj, List<Object> data,
//                                 List<Object> heads, Map<String, Method> methods)
//            throws IllegalArgumentException, IllegalAccessException,
//            InvocationTargetException {
//        for (Map.Entry<String, Method> entry : methods.entrySet()) {
//            Object value = "";
//            int dataIndex = heads.indexOf(entry.getKey());
//            if (dataIndex < data.size()) {
//                value = data.get(heads.indexOf(entry.getKey()));
//            }
//            Method method = entry.getValue();
//            Class<?> param = method.getParameterTypes()[0];
//            if (String.class.equals(param)) {
//                method.invoke(obj, value);
//            } else if (Integer.class.equals(param) || int.class.equals(param)) {
//                if (value.toString() == "") {
//                    value = 0;
//                }
//                method.invoke(obj, new BigDecimal(value.toString()).intValue());
//            } else if (Long.class.equals(param) || long.class.equals(param)) {
//                if (value.toString() == "") {
//                    value = 0;
//                }
//                method.invoke(obj, new BigDecimal(value.toString()).longValue());
//            } else if (Short.class.equals(param) || short.class.equals(param)) {
//                if (value.toString() == "") {
//                    value = 0;
//                }
//                method.invoke(obj, new BigDecimal(value.toString()).shortValue());
//            } else if (Boolean.class.equals(param)
//                    || boolean.class.equals(param)) {
//                method.invoke(obj, Boolean.valueOf(value.toString())
//                        || value.toString().toLowerCase().equals("y"));
//            } else if (JSONObject.class.equals(param)
//                    || JSONObject.class.equals(param)) {
//                method.invoke(obj, JSONObject.parseObject(value.toString()));
//            } else {
//                // Date
//                method.invoke(obj, value);
//            }
//        }
//    }
//
//    private static List<Object> getRow(Row xssfRow) {
//        List<Object> cells = new ArrayList<Object>();
//        if (xssfRow != null) {
//            for (short cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
//                Cell xssfCell = xssfRow.getCell(cellNum);
//                cells.add(getValue(xssfCell));
//            }
//        }
//        return cells;
//    }


    private static String getValue(Cell cell) {
        if (null == cell) {
            return "";
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(cell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(cell.getStringCellValue());
        }
    }

    /**
     * @param excelPath 路径
     * @param rows      行号数组
     * @param cells     列号数组
     * @return
     */
    public static Object[][] datas(String excelPath, int[] rows, int[] cells, int sheetIndex) {
        // 声明二维数组
        Object[][] datas = null;
        try {
            // 获取workbook对象
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            // 获取sheet对象
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            datas = new Object[rows.length][cells.length];
            for (int i = 0; i < rows.length; i++) {
                // 索引要-1
                Row row = sheet.getRow(rows[i] - 1);
                for (int j = 0; j < cells.length; j++) {
                    // 获取列
                    Cell cell = row.getCell(cells[j] - 1);
//                    String value = getValue(cell);
                    // 将列设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
//                    数据保存至二维数组

                    datas[i][j] = value;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * @param excelPath
     * @param sheetIndex
     */
    public static <T> List<T> processExcelToList(Class<T> clazz,String excelPath, int sheetIndex) {
        List<T> clazzs = Lists.newArrayList();
        Map<String,Integer> cellNameCellNumMapping = new HashMap<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new File(excelPath));
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            // 获取首行
            Row titleRow = sheet.getRow(0);
            // 获取末行列号
            int lastCellNum = titleRow.getLastCellNum();
            // 创建字符串数组保存
            String[] fields = new String[lastCellNum];
            // 遍历每列 取出每行字段名 保存至数组
            for (int i = 0; i < lastCellNum; i++) {
                //根据索引获取列 未映射到的列设置为null
                Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //设置列的类型为字符串
                cell.setCellType(CellType.STRING);
                // 获取列值
                String value = cell.getStringCellValue();
                // 截取
                String title = value.substring(0, value.indexOf("("));
                fields[i] = title;
                cellNameCellNumMapping.put(title,i);
            }
            // 反射调用完成 属性赋值成功 一个Case对象包含了一行内容 多列属性
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                // 每一行作为一个Case
                T t = clazz.newInstance();
                // 拿到一个数据行
                Row dataRow = sheet.getRow(i);
                // 假设行对象为空不处理
                if (dataRow == null || isEmptyRow(dataRow)) {
                    continue;
                }
                // 拿到该行的每一列
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();
                    //获取反射对应的方法名setXXX 首字母大写转换防止出错
                    String methodName = "set" + StringUtil.upperCase(fields[j]);
                    // 获取Case.class字节码文件对象 反射set属性
//                    System.out.println(methodName);
                    Method method = clazz.getMethod(methodName, String.class);
                    // 反射调用完成 属性赋值成功 一个Case对象包含了一行内容 多列属性
                    method.invoke(t, cellValue);

                }
                //获取反射对应的方法名setRowNum
                String methodName = "setRowNum";
                String methodName2 = "setCellNameCellNumMapping";
                Method method = clazz.getMethod(methodName, int.class);
                Method method2 = clazz.getMethod(methodName2, Map.class);
                // 反射调用完成 属性赋值成功 一个Case对象包含了一行内容 多列属性
                method.invoke(t, i);
                method2.invoke(t,cellNameCellNumMapping);
                // case对象保存至CaseUtil里面进行后续使用
                clazzs.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return clazzs;
    }

//    private static <T>  void setRowNum(T t,Class<T> clazz, int rowNum) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        //获取反射对应的方法名setXXX 首字母大写转换防止出错
//        String methodName = "setRowNum";
//        // 获取Case.class字节码文件对象 反射set属性
////                    System.out.println(methodName);
//        Method method = clazz.getMethod(methodName, int.class);
//        // 反射调用完成 属性赋值成功 一个Case对象包含了一行内容 多列属性
//        method.invoke(t, rowNum);
//    }

    /**
     * 判断当前行是否为空行
     *
     * @param dataRow
     * @return
     */
    private static boolean isEmptyRow(Row dataRow) {
        int lastCellNum = dataRow.getLastCellNum();
        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = dataRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            String value = cell.getStringCellValue();
            if (value != null && value.trim().length() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param excelPath
     * @param sheetIndex
     * @param result
     */
    public static void writeBackData(String excelPath,int sheetIndex,int cellNum,int rowNum,String result) {
        OutputStream out = null;
        FileInputStream ins = null;
        try {
            ins = new FileInputStream(new File(excelPath));
            // 获取workbook对象
            Workbook workbook = WorkbookFactory.create(ins);
            // 获取sheet对象
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(result);
            out = new FileOutputStream(new File(excelPath));
            workbook.write(out);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (ins != null) {
                    ins.close();
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void writeBackAssertResult(String excelPath,int sheetIndex,int cellNum,int rowNum,String result) {
        OutputStream out = null;
        FileInputStream ins = null;
        try {
            ins = new FileInputStream(new File(excelPath));
            // 获取workbook对象
            Workbook workbook = WorkbookFactory.create(ins);
            // 获取sheet对象
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(result);
            out = new FileOutputStream(new File(excelPath));
            workbook.write(out);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (ins != null) {
                    ins.close();
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String path = "src/main/resources/case01.xls";
        List<Case> cases = processExcelToList(Case.class, path, 1);
        cases.forEach(System.out::println);
        List<Rest> rests = processExcelToList(Rest.class, path, 0);
        rests.forEach(System.out::println);
        rests.forEach(x -> System.out.println(x.getCellNumByCellName("url")+" "+x.getRowNum()));
//        for (Case aCase : cases) {
//            System.out.println(aCase);
//        }
    }
}
