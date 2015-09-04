/**
 * 
 */
package com.salesforce.automation.test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.salesforce.automation.util.Constants;
import com.salesforce.pageobjects.HomePage;
import com.salesforce.pageobjects.LeadsPage;
import com.salesforce.pageobjects.LoginPage;

/**
 * @author xcentric
 * 
 */
public class SalesforceTest extends TestCase {
	WebDriver webDriver;

	/**
	 * @throws java.lang.Exception
	 */

	@Before
	public void setUp() throws Exception {
		if (System.getProperty("webdriver.chrome.driver") != null) {
			webDriver = new ChromeDriver();
		}
		else{
				webDriver = new FirefoxDriver();
			
		}
		webDriver.get("http://login.salesforce.com");

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		webDriver.close();
	}

	@Test()
	public void testFeature() throws Exception {
		LoginPage loginPage = PageFactory.initElements(webDriver,
				LoginPage.class);
		assertEquals(Constants.LOGINPAGETITLE, loginPage.getTitle());
		HomePage homePage = loginPage.loginUser(Constants.USERID,
				Constants.PASSWORD);
		String userName = homePage.getUserNameDisplayed();
		assertNotNull("Login Failed. User name not displayed on Home Screen",
				userName);
		assertEquals(Constants.USERNAME, userName);
		assertEquals(Constants.HOMEPAGETITLE, homePage.getTitle());
		assertTrue("Failed to click Sales Dropdown",
				homePage.clickSalesDropDown("Sales"));
		LeadsPage leadsPage = homePage.selectLeadsTab("Leads");
		assertNotNull("Failed to click Leads Tab",
				homePage.selectLeadsTab("Leads"));
		assertEquals(Constants.LEADSPAGETITLE, leadsPage.getTitle());
		assertTrue("Failed to select Leads Filter",
				leadsPage.filterLeads("All Leads"));
		String value = System.getProperty("value");
		String key =  System.getProperty("key");
		if(value==null){
			value = leadsPage.getDefaultName();
			key = "Name";
		}

		/**
		 * FIXME: unable to switch window Could not complete the Bonus task
		 */
		/**
		 * CampaignPage campaignPage = leadsPage.addToCampaign(key, value);
		 * assertNotNull("Failed to add Lead to campaign", campaignPage); String
		 * currentHandle = campaignPage.getCurrentWindowHandle(); LookUpPage
		 * lookUp = campaignPage.lookUpCampaign("Automation Campaign");
		 * assertNotNull("Failed to Load Look up Page", lookUp); campaignPage =
		 * lookUp.doLookUp("Automation Campaign",currentHandle);
		 * assertNotNull("Failed to Look up Campaign", campaignPage);
		 */

		assertTrue("Cannot find Expected Name in the List",
				leadsPage.deleteLead(key, value));
		assertFalse("Lead found in the List which was expected to be deleted",
				leadsPage.searchNameInTheTable(key, value));

	}

}
