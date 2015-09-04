package com.salesforce.pageobjects;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.salesforce.automation.util.Constants;

public abstract class Page {

	protected WebDriver webDriver;
	private WebDriverWait wait;

	public Page(WebDriver driver) {
		this.webDriver = driver;
		wait = new WebDriverWait(driver, Constants.EXPLICITTIMEOUT);

	}
	
	
	
	public String getTitle() {
		return webDriver.getTitle();
	}
	
	public void waitForAlert() {
			wait.until(ExpectedConditions.alertIsPresent());
	}

	public boolean sendText(final By locator,final String text, final Logger logger){
		try {
			logger.info("Entering text " + text+" @ Locator : "+locator);
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver webDriver) {
					try {
						webDriver.findElement(locator).clear();
						webDriver.findElement(locator).sendKeys(text);
						return true;
					} catch (StaleElementReferenceException e) {
						logger.info(e.toString());
						logger.info("Trying to Search WebElement again");
						return false;
					}
				}
			});
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			return false;
		}
	}

	public boolean clickByLocator(final By locator, final Logger logger) {
		try {
			logger.info("Clicking Locator : " + locator);
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver webDriver) {
					try {
						webDriver.findElement(locator).click();
						return true;
					} catch (StaleElementReferenceException e) {
						logger.info(e.toString());
						logger.info("Trying to click the locator again");
						return false;
					}
				}
			});
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			return false;
		}
	}
	
	public boolean clickByLocator(final By locator, final Logger logger, int time) {
		try {
			logger.info("Clicking Locator : " + locator);
			wait.withTimeout(time, TimeUnit.SECONDS);
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver webDriver) {
					try {
						webDriver.findElement(locator).click();
						return true;
					} catch (StaleElementReferenceException e) {
						logger.info(e.toString());
						logger.info("Trying to click the locator again");
						return false;
					}
				}
			});
			wait.withTimeout(Constants.EXPLICITTIMEOUT, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			return false;
		}
	}

	public boolean waitForVisiblity(final By locator, final Logger logger) {
		try {
			logger.info("Searching for Locator : " + locator);
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver webDriver) {
					try {
						webDriver.findElement(locator);
						return true;
					} catch (StaleElementReferenceException e) {
						logger.info(e.toString());
						logger.info("Trying to Search WebElement again");
						return false;
					}
				}
			});
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			return false;
		}
	}
	
	public boolean waitForVisiblity(final By locator, final Logger logger, int time) {
		try {
			logger.info("Searching for Locator : " + locator);
			wait.withTimeout(time, TimeUnit.SECONDS);
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver webDriver) {
					try {
						webDriver.findElement(locator);
						return true;
					} catch (StaleElementReferenceException e) {
						logger.info(e.toString());
						logger.info("Trying to Search WebElement again");
						return false;
					}
				}
			});
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			return false;
		}
		finally{
			wait.withTimeout(Constants.EXPLICITTIMEOUT, TimeUnit.SECONDS);

		}
	}
	
}
