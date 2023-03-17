package io.github.lawrencez.boilerplate.providers.browser;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import io.github.lawrencez.boilerplate.properties.Constants;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlaywrightBrowserContextProvider implements Provider<BrowserContext> {
	private final Constants constants = Constants.getConstants();
	@Inject
	private Browser browser;

	@Override
	public BrowserContext get() {
		BrowserContext context = browser.newContext(options());
		if (constants.isBrowserTraceEnabled()) {
			context.tracing().start(new Tracing.StartOptions()
					.setScreenshots(true)
					.setSnapshots(true)
					.setSources(false));
		}
		context.setDefaultTimeout(constants.getBrowserTimeout());
		return context;
	}

	private NewContextOptions options() {
		Path path = Paths.get(constants.getBrowserContextAuthStateFile());
		NewContextOptions options = new NewContextOptions();
		options.setBaseURL(constants.getEnvUrl());
		if (Files.exists(path)) {
			options.setStorageStatePath(path);
		}
		return options;
	}
}
