package io.github.lawrencez.boilerplate.specs.saucedemosite;

import io.github.lawrencez.boilerplate.pages.saucedemosite.InventoryPage;
import io.github.lawrencez.boilerplate.testng.browser.BaseBrowserTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Tests for demo purpose
 */
public class DemoTest extends BaseBrowserTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoTest.class);

	@Test
	public void dummyTestToPass() throws Exception {
		LOGGER.info("a dummy test to always pass.");
		Assert.assertEquals(1, 1, "assert equal number");
	}

	@Test(groups = {"smoke"})
	public void demoInventoryPageTest() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		var inventoryPage = createInstance(InventoryPage.class);
		assertThat(inventoryPage.getPage()).hasTitle(Pattern.compile("Swag Labs"));
		int invCount = inventoryPage.getInvCountOnPage();
		int btnCount = inventoryPage.getActionButton(InventoryPage.Button.ADD_TO_CART).count();
		int imgCount = inventoryPage.getInvImgCountOnPage();
		if (inventoryPage.getCartBadgeCount() == 0) {
			softAssert
					.assertEquals(btnCount, invCount, "each item has [Add to cart] button");
		}
		softAssert
				.assertEquals(imgCount, invCount, "each item has an image");
		softAssert.assertAll();
	}

	@Test(groups = {"smoke"})
	public void demoAddToCartTest() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		var inventoryPage = createInstance(InventoryPage.class);
		inventoryPage.getActionButton(InventoryPage.Button.ADD_TO_CART,
				"Sauce Labs Bike Light").click();
		int invCount = inventoryPage.getInvCountOnPage();
		int addToCartBtnCount = inventoryPage.getActionButton(InventoryPage.Button.ADD_TO_CART)
				.count();
		softAssert.assertTrue(invCount > addToCartBtnCount,
				"has added item to cart");

		var removeBtnCount = inventoryPage.getActionButton(InventoryPage.Button.REMOVE).count();
		var cartBadgeCount = inventoryPage.getCartBadgeCount();
		softAssert.assertEquals(cartBadgeCount, removeBtnCount,
				"each added item has [Remove] button");
		softAssert.assertAll();
	}
}