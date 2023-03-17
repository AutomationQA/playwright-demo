package io.github.lawrencez.boilerplate.specs.saucedemosite;

import io.github.lawrencez.boilerplate.pages.saucedemosite.LoginPage;
import io.github.lawrencez.boilerplate.testng.browser.BaseBrowserTest;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseBrowserTest {

	@Test(groups = {"login"})
	public void simpleLoginTest() {
		var loginPage = createInstance(LoginPage.class);
		loginPage.login("standard_user", "secret_sauce");
		assertThat(loginPage.logoutLink()).isEnabled();
	}
}