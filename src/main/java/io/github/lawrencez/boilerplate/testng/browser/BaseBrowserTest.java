package io.github.lawrencez.boilerplate.testng.browser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import io.github.lawrencez.boilerplate.listener.TestConfigListener;
import io.github.lawrencez.boilerplate.listener.TestListener;
import io.github.lawrencez.boilerplate.modules.browser.PlaywrightModule;
import io.github.lawrencez.boilerplate.properties.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Listeners({TestConfigListener.class, TestListener.class})
public class BaseBrowserTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseBrowserTest.class);
	private final ThreadLocal<Injector> injector = new ThreadLocal<>();
	protected Constants constants;

	protected BaseBrowserTest() {
		this.constants = Constants.getConstants();
	}

	@BeforeMethod(alwaysRun = true)
	public void launchBrowserApp(ITestResult result) {
		injector.set(Guice.createInjector(new PlaywrightModule()));
		injector.get().getInstance(Page.class).navigate("/");
		String testName = result.getMethod().getMethodName();
		String[] testGroups = result.getMethod().getGroups();
		LOGGER.debug("[Launch Browser App] Environment Name [{}] for [{}]", constants.getEnvName(), testName);
		LOGGER.debug("[Launch Browser App] Environment Base URL [{}] for [{}]", constants.getEnvUrl(), testName);
		LOGGER.debug("[Launch Browser App] Browser Name [{}] for [{}]", constants.getBrowserName(), testName);
		LOGGER.debug("[Launch Browser App] Browser Headless is [{}] for [{}]", constants.isBrowserHeadlessMode(), testName);
		setupAuthState(testGroups);
	}

	@AfterMethod(alwaysRun = true)
	public void cleanup(ITestResult result) {
		if (constants.isBrowserTraceEnabled()) {
			String fileName = constants.getExtentReportPath()
					+ result.getMethod().getMethodName() + "_trace.zip";
			injector.get().getInstance(BrowserContext.class)
					.tracing().stop(new Tracing.StopOptions()
							.setPath(Paths.get(fileName)));
		}
		injector.get().getInstance(BrowserContext.class).close();
		injector.get().getInstance(Playwright.class).close();
	}

	protected <T extends BaseBrowserPage> T createInstance(Class<T> tClass) {
		try {
			Page page = injector.get().getInstance(Page.class);
			return tClass.getDeclaredConstructor(Page.class).newInstance(page);
		} catch (Exception ex) {
			throw new RuntimeException("Unable to create instance.\n" + ex.getMessage());
		}
	}

	/**
	 * Only save auth state file once at first time
	 * and bypass any tests in the "Login" flow based on test class name
	 *
	 * @param testGroups used to evaluate if authentication setup is needed
	 */
	private void setupAuthState(String[] testGroups) {
		Path path = Paths.get(constants.getBrowserContextAuthStateFile());
		boolean hasAuth = Arrays.asList(testGroups).contains("login");
		if (Files.notExists(path) && !hasAuth) {
			var page = injector.get().getInstance(Page.class);
			// TODO: can add/update with other one time login auth flow,
			//  and to make it global/reusable
			if (constants.getEnvName().equals("saucedemo")) {
				page.getByPlaceholder("Username").fill("standard_user");
				page.getByPlaceholder("Password").fill("secret_sauce");
				page.getByText("Login").click();
			}
			injector.get().getInstance(BrowserContext.class)
					.storageState(new BrowserContext.StorageStateOptions()
							.setPath(Paths.get(constants.getBrowserContextAuthStateFile())));
		}
	}

	public void failOnScreenshot(String fileName) {
		injector.get().getInstance(Page.class)
				.screenshot(new Page.ScreenshotOptions()
						.setPath(Paths.get(fileName))
						.setFullPage(true));
	}
}