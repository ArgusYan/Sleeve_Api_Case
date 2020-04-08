package com.test.api.functions;

/**
 * Function接口
 * 为了实现自定义小函数
 * 类似Jmeter的函数 避免硬编码的去在代码中添加 降低耦合 便于维护
 * 暂未实现：
 * 基本思路将DTO的数据作为被测数据使用之前
 * 通过反射扫描每个属性的属性值 正则判断是否存在形如 "$.{}"
 * 然后进行替换比如$.{};  __Random();
 * 在set属性值
 * 反射得到的当前属性的属性名 得到当前的列号 得到getCaseRow
 * 回写xls对应cell值
 */
public interface Function {
	String execute(String[] args);

	String getReferenceKey();
}
