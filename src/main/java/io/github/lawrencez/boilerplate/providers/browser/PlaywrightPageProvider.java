package io.github.lawrencez.boilerplate.providers.browser;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class PlaywrightPageProvider implements Provider<Page> {
	@Inject
	private BrowserContext browserContext;

	@Override
	public Page get() {
		return browserContext.newPage();
	}
}