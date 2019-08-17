package com.fbleague.infoserver.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fbleague.infoserver.it.InfoserverIT;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class InfoserverE2ETest {
	static Logger logger = LoggerFactory.getLogger(InfoserverIT.class);
	
	@Value("${selenium.settings.browserName}")
	String browserName;	
	
	@Value("${selenium.settings.chromeDriverURL}")
	String chromeDriverURL;	

	@Value("${selenium.settings.leagueAppURL}")
	String leagueAppURL;	
	
	@Test
	public void browseLeagueApp() throws MalformedURLException, InterruptedException {
	    // Create an instance of the driver
		URL local = new URL(chromeDriverURL);
		WebDriver driver = new RemoteWebDriver(local, DesiredCapabilities.chrome());		
		
	    // Navigate to a web page
	    driver.get(leagueAppURL);
	    
	    Thread.sleep(100);
	    
	    // Anticipate web browser response, with an explicit wait
	    WebDriverWait wait = new WebDriverWait(driver, 15);
	    
	    WebElement element = driver.findElement(By.name("clist"));
	    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.name("clist"), 0));

	    Select dd = new Select(element);
	    int size = dd.getOptions().size();
	    logger.info("Total elements in the list is: " + size);
	    
	    for(int i=1; i < size; i++) {
	    	dd.selectByIndex(i);
	    	logger.info("Selected option is: " + dd.getFirstSelectedOption().getText());
	    	String team = dd.getFirstSelectedOption().getText().split("Team=")[1];
	    	wait.until(ExpectedConditions.textToBePresentInElement(
	    			By.xpath("//table/tbody/tr[3]/td[2]"), team));
	    	String teamInTable = driver.findElement(By.xpath("//table/tbody/tr[3]/td[2]"))
	    			.getText().split("-")[1].trim();
	    	assertThat(team).isEqualTo(teamInTable);
	    	logger.info("Overall position of {} is: {}", team, 
	    			driver.findElement(By.xpath("//table/tbody/tr[4]/td[2]")).getText());
	    }
	    
	    
	    // Conclude a test
	    driver.quit();
	    
		Integer someValue = 1;
		assertThat(someValue).isEqualTo(1);		
	}

}
