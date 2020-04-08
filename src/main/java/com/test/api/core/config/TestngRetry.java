package com.test.api.core.config;

import com.test.api.core.exception.ErrorRespStatusException;
import com.test.api.utils.ReportUtil;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;


public class TestngRetry implements IRetryAnalyzer {
	// 设置当前失败执行的次数
	private int retryCount = 1;
	// 设置最大失败执行次数
	private static int maxRetryCount = 2;

	@Override
	public boolean retry(ITestResult iTestResult) {
		if(retryCount < maxRetryCount) {
			retryCount++;
			return true;
		}
		return false;
	}

}

