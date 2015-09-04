package com.salesforce.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends Page {
	final static Logger logger = Logger.getLogger(HomePage.class);

	private By currentUserNameLocator = By
			.xpath(".//*[@class='currentStatusUserName']");
	private By getTabLocator(String tabName) {
		return By
				.xpath(".//*[@class='zen']/div[contains(@class,'zen-hasTabOrganizer')]//*/li/a[text()='"
						+ tabName + "']");
	}
	private By getDropDownLocator(String dropDownItem) {
		return By
				.xpath(".//*[@id='toolbar']/div/div/span[(@class='menuButtonLabel') and text()='"
						+ dropDownItem
						+ "']/../../div[@class='menuButtonButton']");
	}
	public boolean clickSalesDropDown(String dropDownItem) {
		return clickByLocator(getDropDownLocator(dropDownItem), logger);
	}

	public LeadsPage selectLeadsTab(String tabName) {
		 clickByLocator(getTabLocator(tabName), logger);
		 return PageFactory.initElements(webDriver, LeadsPage.class);
	}
	
	public HomePage(WebDriver webDriver) {
		super(webDriver);
		if (!waitForVisiblity(currentUserNameLocator, logger)) {
			throw new IllegalStateException("Failed to Load the Home Page");
		}
	}

	
	public String getUserNameDisplayed() {
		if (waitForVisiblity(currentUserNameLocator, logger)) {
			return webDriver.findElement(currentUserNameLocator).getText();
		}
		return null;
	}
	
	




	
}
