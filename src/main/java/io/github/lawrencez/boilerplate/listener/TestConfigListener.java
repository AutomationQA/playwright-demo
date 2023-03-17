package io.github.lawrencez.boilerplate.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IConfigurationListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class TestConfigListener implements IConfigurationListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestConfigListener.class);

	@Override
	public void onConfigurationSuccess(ITestResult tr, ITestNGMethod tm) {
		String configName = tr.getName();
		String testClass = tr.getInstanceName().replaceAll(".+\\.", "");
		String testName = tm.getMethodName();
		LOGGER.debug("[CONFIG PASS] [{}] for [{}]->[{}]", configName, testClass, testName);
	}

	@Override
	public void onConfigurationFailure(ITestResult tr, ITestNGMethod tm) {
		String configName = tr.getName();
		String testClass = tr.getInstanceName().replaceAll(".+\\.", "");
		String testName = tm.getMethodName();
		LOGGER.error("[CONFIG FAIL] [{}] for [{}]->[{}]", configName, testClass, testName);
	}

	@Override
	public void onConfigurationSkip(ITestResult tr, ITestNGMethod tm) {
		String configName = tr.getName();
		String testClass = tr.getInstanceName().replaceAll(".+\\.", "");
		String testName = tm.getMethodName();
		LOGGER.error("[CONFIG SKIP] [{}] for [{}]->[{}]", configName, testClass, testName);
	}
}