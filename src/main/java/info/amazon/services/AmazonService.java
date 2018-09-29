package info.amazon.services;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import info.amazon.models.User;

public class AmazonService {
	private volatile boolean isLoaded = false;

	private String baseUrl = "http://www.amazon.com";
	private WebDriver webDriver;

	static {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
	}

	public void closeDriver() {
		if (isLoaded) {
			webDriver.quit();
		}
	}

	public void findProductByURL(String url) {
		if(url.contains("http://") | url.contains("https://")) {
		getDriver().get(url);
		} else {
			getDriver().get("https://" + url);
		}
	}

	public void findProductByASIN(String asin) { // B07FH83C2T
		WebDriver driver = getDriver();
		driver.get(baseUrl);
		WebElement element = driver.findElement(By.cssSelector("#twotabsearchtextbox"));
		element.sendKeys(asin);
		element.submit();
		if (chechNegativeSearchResult()) {
			return;
		}
		element = driver.findElement(By.id("result_0"));
		if (element.getAttribute("data-asin").equals(asin)) {
			WebElement secondElement = element.findElement(
					By.cssSelector(".a-link-normal.s-access-detail-page.s-color-twister-title-link.a-text-normal"));
			secondElement.click();
		}

	}

	public void regNewUser(User user) {
		WebDriver driver = getDriver();
		driver.findElement(By.cssSelector("#nav-link-accountList.nav-a.nav-a-2")).click();
		driver.findElement(By.cssSelector("#createAccountSubmit")).click();
		driver.findElement(By.cssSelector("#ap_customer_name")).sendKeys(user.getFirstName());
		driver.findElement(By.cssSelector("#ap_email")).sendKeys(user.getEmail());
		driver.findElement(By.cssSelector("#ap_password")).sendKeys(user.getPassword());
		driver.findElement(By.cssSelector("#ap_password_check")).sendKeys(user.getPassword());
		driver.findElement(By.cssSelector("#a-autoid-0.a-button.a-button-normal.a-button-span12.a-button-primary"))
				.click();
	}

	public void addProductToCartByURL(User user, String product) {
		WebDriver driver = getDriver();
		if (checkSignIn()) {
			signIn(user);
		}
		findProductByURL(product);
		WebElement firstElement = driver.findElement(By.id("add-to-cart-button"));
		firstElement.click();
		firstElement = driver.findElement(By.id("nav-cart-count"));
		firstElement.click();
	}

	public void addProductToCartByASIN(User user, String product) {
		WebDriver driver = getDriver();
		if (checkSignIn()) {
			signIn(user);
		}
		findProductByASIN(product);
		if (chechNegativeSearchResult()) {
			return;
		}
		WebElement firstElement = driver.findElement(By.id("add-to-cart-button"));
		firstElement.click();
		firstElement = driver.findElement(By.id("nav-cart-count"));
		firstElement.click();
	}

	public boolean checkSignIn() {
		WebDriver driver = getDriver();
		driver.get(baseUrl);
		WebElement firstElement;
		firstElement = driver.findElement(By.cssSelector("#nav-link-accountList.nav-a.nav-a-2"));
		return firstElement.getText().contains("Hello. Sign in");
	}

	private void signIn(User user) {
		WebDriver driver = getDriver();
		WebElement firstElement = driver.findElement(By.id("nav-link-accountList"));
		firstElement.click();
		firstElement = driver.findElement(By.id("ap_email"));
		firstElement.sendKeys(user.getEmail());
		firstElement.submit();
		firstElement = driver.findElement(By.id("ap_password"));
		firstElement.sendKeys(user.getPassword());
		firstElement.submit();
	}

	private boolean chechNegativeSearchResult() {
		try {
			getDriver().findElement(By.id("noResultsTitle"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private void initDriver() {
		webDriver = new ChromeDriver();
	}

	private void checkWindowIsAlive() {
		try {
			webDriver.getTitle();
		} catch (Exception e) {
			closeDriver();
			isLoaded = false;
		}
	}

	private WebDriver getDriver() {
		if (isLoaded) {
			checkWindowIsAlive();
		}
		if (!isLoaded) {
			initDriver();
			isLoaded = true;
		}
		return webDriver;
	}
}
