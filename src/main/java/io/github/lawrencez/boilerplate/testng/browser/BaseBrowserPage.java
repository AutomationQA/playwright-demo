package io.github.lawrencez.boilerplate.testng.browser;

import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import lombok.Getter;

public class BaseBrowserPage {
	@Getter
	protected Page page;

	@Inject
	public BaseBrowserPage(Page page) {
		this.page = page;
	}

	protected <T extends BaseBrowserPage> T createPageInstance(Class<T> tClass) {
		try {
			return tClass.getDeclaredConstructor(Page.class).newInstance(page);
		} catch (Exception ex) {
			throw new RuntimeException("Error creating playwright browser page Instance.\n" + ex.getMessage());
		}
	}
}