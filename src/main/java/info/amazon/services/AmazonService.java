package info.amazon.services;

import org.openqa.selenium.By;
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
		getDriver().get(url);
	}
	
	public void findProductByASIN(String asin) {
		getDriver().get(baseUrl);
		WebElement element = getDriver().findElement(By.cssSelector("#twotabsearchtextbox"));
		element.click();
		element.sendKeys(asin);
		element.submit();
	}

	public void regNewUser(User user) {

		WebDriver driver = getDriver();
		driver.get(baseUrl);
		driver.findElement(By.cssSelector("#nav-link-accountList.nav-a.nav-a-2")).click();
		driver.findElement(By.cssSelector("#createAccountSubmit")).click();
		driver.findElement(By.cssSelector("#ap_customer_name")).sendKeys(user.getFirstName());
		driver.findElement(By.cssSelector("#ap_email")).sendKeys(user.getEmail());
		driver.findElement(By.cssSelector("#ap_password")).sendKeys(user.getPassword());
		driver.findElement(By.cssSelector("#ap_password_check")).sendKeys(user.getPassword());
		driver.findElement(By.cssSelector("#a-autoid-0.a-button.a-button-normal.a-button-span12.a-button-primary"))
				.click();
	}

	public void addProductToCart(User user, String product) {
		WebDriver driver = getDriver();
		driver.get(baseUrl);
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