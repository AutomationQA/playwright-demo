package io.github.lawrencez.boilerplate.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.lawrencez.boilerplate.properties.Constants;
import io.github.lawrencez.boilerplate.utils.datetime.JDate;

public class ExtentReportManager {
	private static final Constants constants = Constants.getConstants();

	private ExtentReportManager() {
	}

	public static ExtentReports createReport() {
		String timeStamp = JDate.getDateWithTime("MM-dd-yy-HH-mm-ss");
		String fileName = String.format("%sExtentReport_%s.html", constants.getExtentReportPath(), timeStamp);

		ExtentReports extentReport = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(fileName);
		extentReport.attachReporter(spark);

		extentReport.setSystemInfo("Platform", System.getProperty("os.name"));
		extentReport.setSystemInfo("Version", System.getProperty("os.version"));
		extentReport.setSystemInfo("Test Env", constants.getEnvUrl());
		extentReport.setSystemInfo("Test Browser", constants.getBrowserName());
		extentReport.setSystemInfo("Test Group", System.getProperty("test.group"));
		extentReport.setSystemInfo("Test Suite", System.getProperty("test.suite"));

		return extentReport;
	}
}