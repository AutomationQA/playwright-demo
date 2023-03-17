package io.github.lawrencez.boilerplate.pages.saucedemosite;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.github.lawrencez.boilerplate.testng.browser.BaseBrowserPage;
import lombok.Getter;

public class InventoryPage extends BaseBrowserPage {
	public InventoryPage(Page page) {
		super(page);
		page.navigate("/inventory.html");
	}

	public int getInvCountOnPage() {
		return page.locator("div.inventory_item").count();
	}

	public Locator getActionButton(Button button) {
		return page.getByRole(AriaRole.BUTTON,
				new Page.GetByRoleOptions().setName(button.getLabel()));
	}

	public Locator getActionButton(Button button, String invItemName) {
		return page.locator("div.inventory_item_description")
				.filter(new Locator.FilterOptions().setHasText(invItemName))
				.getByRole(AriaRole.BUTTON,
						new Locator.GetByRoleOptions().setName(button.getLabel()));
	}

	public int getInvImgCountOnPage() {
		return page.locator("a img").count();
	}

	public int getCartBadgeCount() {
		var cartBadge = page.locator("a.shopping_cart_link").textContent();
		return cartBadge.isBlank() ? 0 : Integer.parseInt(cartBadge);
	}

	public enum Button {
		REMOVE("Remove"), ADD_TO_CART("Add to cart");

		@Getter
		private final String label;

		private Button(final String label) {
			this.label = label;
		}
	}
}