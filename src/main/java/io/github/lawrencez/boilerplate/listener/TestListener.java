package io.github.lawrencez.boilerplate.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.github.lawrencez.boilerplate.properties.Constants;
import io.github.lawrencez.boilerplate.testng.browser.BaseBrowserTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class TestListener extends BaseBrowserTest implements ITestListener {
	private static final ExtentReports REPORT = ExtentReportManager.createReport();
	private static final ThreadLocal<ExtentTest> TEST_THREAD_LOCAL = new ThreadLocal<>();
	private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);
	private final Constants constants = Constants.getConstants();

	@Override
	public void onTestStart(ITestResult result) {
		String testClass = result.getTestClass().getName().replaceAll(".+\\.", "");
		String testName = result.getMethod().getMethodName();
		LOGGER.debug("[TEST START] [{}]->[{}]", testClass, testName);
		ExtentTest test = REPORT.createTest(String.format("[%s] %s", testClass, testName));
		TEST_THREAD_LOCAL.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testClass = result.getTestClass().getName().replaceAll(".+\\.", "");
		String testName = result.getMethod().getMethodName();
		LOGGER.debug("[TEST PASS] [{}]->[{}]", testClass, testName);
		TEST_THREAD_LOCAL.get().assignCategory(testClass)
				.log(Status.PASS, String.format("<b>[TEST PASS]:</b> [%s]->[%s]", testClass, testName))
				.log(Status.PASS, String.format("<b>[Execution TIME]:</b> %.2f seconds",
						(double) (result.getEndMillis() - result.getStartMillis()) / 1000));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testClass = result.getInstanceName().replaceAll(".+\\.", "");
		String testName = result.getMethod().getMethodName();
		LOGGER.error("[TEST FAIL] [{}]->[{}]", testClass, testName);
		TEST_THREAD_LOCAL.get().assignCategory(testClass)
				.log(Status.FAIL, String.format("<b>[TEST FAIL]:</b> [%s]->[%s]", testClass, testName))
				.log(Status.FAIL, String.format("<b>[Execution TIME]:</b> %.2f seconds",
						(double) (result.getEndMillis() - result.getStartMillis()) / 1000))
				.log(Status.FAIL, result.getThrowable());
		if (constants.isBrowserTraceEnabled()) {
			String traceFile = testName + "_trace.zip";
			TEST_THREAD_LOCAL.get().log(Status.FAIL, String.format("<a href='%s'>click to download trace</a>", traceFile));
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testClass = result.getTestClass().getName().replaceAll(".+\\.", "");
		String testName = result.getMethod().getMethodName();
		LOGGER.debug("[TEST SKIP] [{}]->[{}]", testClass, testName);
		TEST_THREAD_LOCAL.get().assignCategory(testClass)
				.log(Status.SKIP, String.format("<b>[TEST SKIP]:</b> [%s]->[%s]", testClass, testName));
	}

	@Override
	public void onFinish(ITestContext context) {
		REPORT.flush();
	}

	private File getUniqueFileName(String possibleFileName) {
		File tmpFile = new File(constants.getExtentReportPath() + possibleFileName + ".png");
		int i = 1;
		while (tmpFile.exists()) {
			tmpFile = new File(constants.getExtentReportPath() + possibleFileName + i + ".png");
			i++;
		}
		return tmpFile;
	}
}