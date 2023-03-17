package io.github.lawrencez.boilerplate.specs.saucedemosite;

import io.github.lawrencez.boilerplate.pages.saucedemosite.InventoryPage;
import io.github.lawrencez.boilerplate.testng.browser.BaseBrowserTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Tests for demo purpose
 */
public class AnotherDemoTest extends BaseBrowserTest {

	@Test
	public void oneMoreSimpleDummyTest() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(3, 3, "assert equal number");
		softAssert.assertEquals(5, 5, "assert equal number");
		softAssert.assertAll();
	}

	@Test
	public void noLoginTest() throws Exception {
		var inventoryPage = createInstance(InventoryPage.class);
		inventoryPage.getPage().navigate("/inventory.html");
		assertThat(inventoryPage.getPage()).hasTitle(Pattern.compile("Swag Labs"));
	}
}