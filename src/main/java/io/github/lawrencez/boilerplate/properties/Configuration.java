package io.github.lawrencez.boilerplate.properties;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties", "classpath:config.properties"})
public interface Configuration extends Config {
	@Key("env.name")
	String envName();

	@Key("env.url")
	String envUrl();

	@Key("browser.name")
	String browserName();

	@Key("headless")
	boolean browserHeadless();

	@Key("slow.motion.by.millis")
	double slowMotionByMillis();

	@Key("timeout.millis")
	double timeoutMillis();

	@Key("developer.tools")
	boolean devTools();

	@Key("enable.trace")
	boolean enableTrace();

	@Key("auth.state.file")
	String authStateFile();

	@Key("device.emulation.type")
	String deviceType();

	@Key("locale")
	String deviceLocale();

	@Key("report.target")
	String reportTarget();
}