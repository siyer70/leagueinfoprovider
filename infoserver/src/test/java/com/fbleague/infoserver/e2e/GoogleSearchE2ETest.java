package com.fbleague.infoserver.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
 
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class GoogleSearchE2ETest {
	
	@Test
	public void browseLeagueApp() throws MalformedURLException {
		final String TEXT_TO_SEARCH = "Selenium";
		
	    // Create an instance of the driver
		URL local = new URL("http://localhost:9515");
		WebDriver driver = new RemoteWebDriver(local, DesiredCapabilities.chrome());		
		
//	    WebDriver driver = new FirefoxDriver();
	 
	    // Navigate to a web page
	    driver.get("http://www.google.com");
	    
	    WebElement element = driver.findElement(By.name("q"));
	    element.sendKeys(TEXT_TO_SEARCH);
	    element.sendKeys(Keys.RETURN);
//	    driver.findElement(By.name("btnK")).click();	    
	    
	    // Anticipate web browser response, with an explicit wait
	    WebDriverWait wait = new WebDriverWait(driver, 10);
	    wait.until(ExpectedConditions.titleIs(TEXT_TO_SEARCH + " - Google Search"));
	    
		
	    // Conclude a test
	    driver.quit();
	    
		Integer someValue = 1;
		assertThat(someValue).isEqualTo(1);		
	}

}
