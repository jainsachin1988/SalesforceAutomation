package com.salesforce.pageobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.salesforce.automation.util.Constants.Alert;

public class LeadsPage extends Page {
	public LeadsPage(WebDriver driver) {
		super(driver);
		 if (!waitForVisiblity(getActiveTabLocator(), logger)) {
	            throw new IllegalStateException("Failed to Load the Leads Page");
	        }
	}


	final static Logger logger = Logger.getLogger(LeadsPage.class);
	private static int totalPageCount;

	private By nextPageLocator = By
			.xpath(".//*[@class='paginator']/span[@class='prevNextLinks']/span[@class='prevNext']/a/img[@class='next']");

	private By firstPageLocator = By
			.xpath(".//*[@class='paginator']/span[@class='prevNextLinks']/span[@class='prevNext']/a/img[@class='first']");

	private By getActiveTabLocator() {
		return By
				.xpath(".//*[@class='zen']/div[contains(@class,'zen-hasTabOrganizer')]//*/li/a[text()='Leads']/../span");
	}
	private boolean gotoNextPage() {
		logger.info("Navigating to Next Page");
		return clickByLocator(nextPageLocator, logger,5);
	}

	private void gotoFirstPage() throws Exception {
		logger.info("Navigating to First Page");
		clickByLocator(firstPageLocator, logger,5);
	}

	
	private By getInputBoxFromLocator(String key, String value) {
		return By
				.xpath(".//*[@class='listViewport leadBlock']//tr/td[count(//tr/td//*[text()='"
						+ key
						+ "']/..//preceding-sibling::*)]//*[text()='"
						+ value + "']/../../..//parent::tr//input");
	}

	private By getDeleteButtonFromTableRow(String key, String value) {
		return By
				.xpath(".//*[@class='listViewport leadBlock']//tr/td[count(//tr/td//*[text()='"
						+ key
						+ "']/..//preceding-sibling::*)]//*[text()='"
						+ value
						+ "']/../../..//parent::tr/td//span[text()='Del']");
	}

	private By addtoCampaignLocator() {
		return By
				.xpath(".//*[@class='listViewport leadBlock']//div[@class='subNav']//*[@title='Add to Campaign']");
	}

	private By leadsFilterLocator = By
			.xpath(".//*[@class='filterOverview']//select[@id='fcf']");
	private By filterFormLocator = By
			.xpath(".//*[@class='filterOverview']/form");

	private By currentPageNumberLocator = By
			.xpath(".//*[@class='paginator']/span[@class='right']/input");
	private By totalPageCountLocator = By
			.xpath(".//*[@class='paginator']/span[@class='right']");
	private By defaultNameLocator = By.xpath(".//*[@class='x-grid3-row x-grid3-row-first']//tr[1]/td[5]//span");
	@SuppressWarnings("unused")
	private boolean goToPageNum(int pageNum) {
		logger.info("Navigating to  Page Num : " + pageNum);
		if (waitForVisiblity(totalPageCountLocator, logger)) {
			if (totalPageCount == 0) {
				String countText = webDriver.findElement(totalPageCountLocator)
						.getText();
				Pattern p = Pattern.compile("-?[0-9]+(?:,[0-9]+)?");
				Matcher m = p.matcher(countText);
				    while (m.find()) {
				        totalPageCount = Integer.parseInt(m.group(0));
				    }
			}
			if (pageNum > totalPageCount) {
				return false;
			} else {
				sendText(currentPageNumberLocator, String.valueOf(pageNum),
						logger);
				webDriver.findElement(currentPageNumberLocator).sendKeys(Keys.ENTER);
				return true;
			}
		} else {
			return false;
		}
	}
	
	public String getDefaultName(){
		waitForVisiblity(defaultNameLocator, logger);
		return webDriver.findElement(defaultNameLocator).getText();
	}

	private boolean selectLeadsFilter(String value) {
		if (waitForVisiblity(leadsFilterLocator, logger)) {
			Select select = new Select(
					webDriver.findElement(leadsFilterLocator));
			select.selectByVisibleText(value);
			return true;
		}
		return false;
	}

	private void submitFilter() {
		webDriver.findElement(filterFormLocator).submit();
	}

	public boolean filterLeads(String value) {
		if (selectLeadsFilter(value)) {
			submitFilter();
			return true;
		}
		return false;
	}

	private boolean selectCheckBox(String key, String value) {
		return clickByLocator(getInputBoxFromLocator(key, value), logger,5);
	}

	public void handleAlert(Alert alert) throws Exception{
		waitForAlert();
		if (alert == Alert.ACCEPT)
			webDriver.switchTo().alert().accept();
		else if (alert == Alert.REJECT)
			webDriver.switchTo().alert().dismiss();
	}

	public boolean deleteLead(String key, String value) {
		try {
			boolean found = false;
			while (!found) {
				found = selectCheckBox(key, value);
				if (found) {
					clickDeleteLead(key, value);
					handleAlert(Alert.ACCEPT);
					logger.info("Lead : " + value + " deleted!");
					return true;
				} else {
					boolean goToNextPageIfPresent = gotoNextPage();
					if (goToNextPageIfPresent) {
						continue;
					} else {
						return false;
					}
				}
			}
			return found;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Expection occurred: " + e.getMessage());
			return false;
		}
	}

	public void clickDeleteLead(String key, String value) throws Exception {
		clickByLocator(getDeleteButtonFromTableRow(key, value), logger,5);
	}

	public boolean searchNameInTheTable(String key, String value) {
		try {
			gotoFirstPage();
			boolean found = false;
			while (!found) {
				found = waitForVisiblity(getInputBoxFromLocator(key, value),
						logger,5);
				if (found) {
					return true;
				} else {
					boolean goToNextPageIfPresent = gotoNextPage();
					if (goToNextPageIfPresent) {
						continue;
					} else {
						return false;
					}
				}
			}
			return found;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Expection occurred: " + e.getMessage());
			return false;
		}
	}

	public void clickAddToCampaignButton() throws Exception {
		clickByLocator(addtoCampaignLocator(), logger);

	}

	public CampaignPage addToCampaign(String key, String value) {
		try {
			gotoFirstPage();
			CampaignPage campaignPage = null;
			boolean found = false;
			while (!found) {
				found = selectCheckBox(key, value);
				if (found) {
					clickAddToCampaignButton();
					campaignPage = PageFactory.initElements(webDriver,
							CampaignPage.class);
					return campaignPage;
				} else {
					boolean goToNextPageIfPresent = gotoNextPage();
					if (goToNextPageIfPresent) {
						continue;
					} else {
						return campaignPage;
					}
				}
			}
			return campaignPage;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Expection occurred: " + e.getMessage());
			return null;
		}

	}
}
