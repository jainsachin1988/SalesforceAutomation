package com.salesforce.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CampaignPage extends Page {
	final static Logger logger = Logger.getLogger(CampaignPage.class);

	By campaignLocation = By.xpath(".//*[@class='lookupInput']/input");
	By lookUpWindowLocator = By.xpath(".//*[@class='lookupInput']/a");

	public CampaignPage(WebDriver driver) {
		super(driver);
		 if (!waitForVisiblity(lookUpWindowLocator, logger)) {
	            throw new IllegalStateException("Failed to Load the Campaign Page");
	        }
	}

	public String getCurrentWindowHandle() {
		return webDriver.getWindowHandle();
	}

	private void enterTextIntoLookUpField(String text) throws Exception {
		if(!sendText(campaignLocation, text, logger)){
			throw new Exception("Failed to Enter Text");
		}
	}

	private void clickLookUpIcon() throws Exception {
		if(!clickByLocator(lookUpWindowLocator, logger)){
			throw new Exception("Failed to Click Look Up Icon");
		}
	}
	public LookUpPage lookUpCampaign(String text) throws Exception {
		try {
			enterTextIntoLookUpField(text);
			clickLookUpIcon();
			for (String windowHandles : webDriver.getWindowHandles()) {
				webDriver.switchTo().window(windowHandles);
			}
			return PageFactory.initElements(webDriver, LookUpPage.class);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception Occurred "+e.getMessage());
			throw new Exception(e);
		}
	}

}
