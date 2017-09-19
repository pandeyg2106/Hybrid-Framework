package com.seleniumHybridFramework.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
	
	public WebDriver driver;
	public Properties OR;
	public File f1;
	public FileInputStream file;
	
	public void getBrowser(String browser){
		
		if(System.getProperty("os.name").contains("Window")){
			if(browser.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				driver = new ChromeDriver();	
			}
			else if(browser.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			}			
		}
		else if(System.getProperty("os.name").contains("Mac")){
			if(browser.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver");
				driver = new ChromeDriver();
			}
			else if(browser.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir")+"/drivers/geckodriver");
				driver = new FirefoxDriver();
			}
			
		}
	}
	
	public void loadPropertoiesFile() throws IOException{
		OR = new Properties();
		f1 = new File(System.getProperty("user.dir")+"/src/main/java/com/seleniumHybridFramework/config/config.properties");
		file = new FileInputStream(f1);
		OR.load(file);
		
		f1 = new File(System.getProperty("user.dir")+"/src/main/java/com/seleniumHybridFramework/config/or.properties");
		file = new FileInputStream(f1);
		OR.load(file);
	}
	
	public void getScreenShot(String imageName) throws IOException{
		
		if(imageName.equals("")){
			imageName = "_blank";
		}
		
		File image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String imageLocation = System.getProperty("user.dir")+"/src/main/java/com/seleniumHybridFramework/screenshot/";
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_mm_yyyy_hh_mm_ss");
		String actualImageName = imageLocation + imageName + "_" + formater.format(calendar.getTime()) + ".png";
		File destFile = new File(actualImageName);
		FileUtils.copyFile(image, destFile);
	}
	
	public WebElement waitForElement(WebDriver driver, long time, WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public WebElement waitForElementWithPollingInterval(WebDriver driver, long time, WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void implicitWait(long time){
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	public void getPropertiesData(){
		
	}
	
	public static void main(String[] args) throws IOException {
		TestBase test = new TestBase();
		test.loadPropertoiesFile();
		System.out.println(test.OR.getProperty("url"));
		System.out.println(test.OR.getProperty("testname"));
	}

}
