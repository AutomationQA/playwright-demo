package io.github.lawrencez.boilerplate.modules.browser;

import com.google.inject.AbstractModule;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.github.lawrencez.boilerplate.providers.browser.PlaywrightBrowserContextProvider;
import io.github.lawrencez.boilerplate.providers.browser.PlaywrightBrowserProvider;
import io.github.lawrencez.boilerplate.providers.browser.PlaywrightPageProvider;
import io.github.lawrencez.boilerplate.providers.browser.PlaywrightProvider;

public class PlaywrightModule extends AbstractModule {

	@Override
	public void configure() {
		bind(Playwright.class).toProvider(PlaywrightProvider.class).asEagerSingleton();
		bind(Browser.class).toProvider(PlaywrightBrowserProvider.class).asEagerSingleton();
		bind(BrowserContext.class).toProvider(PlaywrightBrowserContextProvider.class).asEagerSingleton();
		bind(Page.class).toProvider(PlaywrightPageProvider.class).asEagerSingleton();
	}
}