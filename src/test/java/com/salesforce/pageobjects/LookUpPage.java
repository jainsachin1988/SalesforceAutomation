package com.salesforce.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class LookUpPage extends Page {
	final static Logger logger = Logger.getLogger(LookUpPage.class);

	public LookUpPage(WebDriver driver) {
		super(driver);
		System.out.println(webDriver.getTitle());
		if (!waitForVisiblity(campaignSearchButton, logger)) {
			throw new IllegalStateException("Failed to Load the Lookup Page");
		}
	}

	private By campaignSearchButton = By
			.xpath(".//*[@id='theForm']//input[@class='btn']");

	private By getCampaignFromTable(String campaignName) {
		return By
				.xpath(".//*[@class='listRelatedObject lookupBlock']//*[@class='pbBody']//tr/th[1]//*[text()='"
						+ campaignName + "']");
	}

	private By campaignDropDownLocator() {
		return By.xpath(".//*[@id='campaignScope']");
	}

	private void selectCampaignDropDown(String dropDownOption) {
		clickByLocator(campaignDropDownLocator(), logger);
		Select dropdown = new Select(webDriver.findElement(campaignDropDownLocator()));
		dropdown.selectByVisibleText(dropDownOption);
	}
	

	private void submitCampaign() throws Exception {
		if (!clickByLocator(campaignSearchButton, logger)) {
			throw new Exception("Failed to Submit Campaign");
		}
	}

	private void clickCampaignFromTable(String campaignName) throws Exception {
		if (!clickByLocator(getCampaignFromTable(campaignName), logger)) {
			throw new Exception("Failed to Click Campaign from Table");
		}
	}

	public CampaignPage doLookUp(String campaignName,
			String handleBackToCampaignPage) throws Exception {
		try {
			selectCampaignDropDown("All Campaigns");
			submitCampaign();
			clickCampaignFromTable(campaignName);
			return PageFactory.initElements(
					webDriver.switchTo().window(handleBackToCampaignPage),
					CampaignPage.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw new Exception(e);
		}

	}
}
