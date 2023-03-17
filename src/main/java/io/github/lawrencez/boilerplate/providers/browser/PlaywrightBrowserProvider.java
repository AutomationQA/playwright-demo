package io.github.lawrencez.boilerplate.providers.browser;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;
import io.github.lawrencez.boilerplate.properties.Constants;

public class PlaywrightBrowserProvider implements Provider<Browser> {
	private final Constants constants = Constants.getConstants();
	private final String browser;
	@Inject
	private Playwright playwright;

	public PlaywrightBrowserProvider() {
		browser = constants.getBrowserName();
	}

	@Override
	public Browser get() {
		return switch (browser.trim().toLowerCase()) {
			case "chrome" -> playwright.chromium().launch(options());
			case "firefox" -> playwright.firefox().launch(options());
			case "safari" -> playwright.webkit().launch(options());
			default -> null;
		};
	}

	private LaunchOptions options() {
		return new LaunchOptions()
				.setHeadless(constants.isBrowserHeadlessMode())
				.setSlowMo(constants.getBrowserSlowMotion());
	}
}