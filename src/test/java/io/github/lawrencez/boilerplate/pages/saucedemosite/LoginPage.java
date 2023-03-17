package io.github.lawrencez.boilerplate.pages.saucedemosite;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.lawrencez.boilerplate.testng.browser.BaseBrowserPage;

public class LoginPage extends BaseBrowserPage {

	public LoginPage(Page page) {
		super(page);
	}

	public LoginPage login(String userName, String password) {
		page.getByPlaceholder("Username").fill(userName);
		page.getByPlaceholder("Password").fill(password);
		page.getByText("Login").click();
		return this;
	}

	public Locator logoutLink() {
		return page.getByText("Logout");
	}
}