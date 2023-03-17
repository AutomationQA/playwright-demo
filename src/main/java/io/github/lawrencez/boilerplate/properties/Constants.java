package io.github.lawrencez.boilerplate.properties;

import lombok.Getter;
import lombok.Setter;
import org.aeonbits.owner.ConfigFactory;

import java.io.File;

@Getter
@Setter
public class Constants {
	private static Constants constants;
	private final String slash = File.separator;
	Configuration configuration = ConfigFactory.create(Configuration.class);
	private final String envName = configuration.envName();
	private final String envUrl = configuration.envUrl();
	private final String extentReportPath = System.getProperty("user.dir")
			+ slash + configuration.reportTarget()
			+ slash + "reports" + slash + "extent" + slash;
	private final String browserName = configuration.browserName();
	private final boolean browserHeadlessMode = configuration.browserHeadless();
	private final boolean browserDevToolsMode = configuration.devTools();
	private final boolean browserTraceEnabled = configuration.enableTrace();
	private final String browserDeviceLocale = configuration.deviceLocale();
	private final String browserDeviceType = configuration.deviceType();
	private final String browserContextAuthStateFile = System.getProperty("user.dir")
			+ slash + configuration.reportTarget()
			+ slash + configuration.authStateFile();
	private final double browserSlowMotion = configuration.slowMotionByMillis();
	private final double browserTimeout = configuration.timeoutMillis();
	private String traceFileName;

	private Constants() {
	}

	public static Constants getConstants() {
		if (constants == null) {
			constants = new Constants();
		}
		return constants;
	}
}