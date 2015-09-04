package com.salesforce.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends Page {
	final static Logger logger = Logger.getLogger(LoginPage.class);
	By userIdLocator = By.id("username");
	By passwordLocator = By.id("password");
	By submitButton = By.id("Login");

	public LoginPage(WebDriver webDriver) {
		super(webDriver);
		 if (!waitForVisiblity(userIdLocator, logger)) {
	            throw new IllegalStateException("Failed to Load the Home Page");
	        }
	}

	public String getTitle() {
		return webDriver.getTitle();
	}

	private void enterUserId(String userId) throws Exception {
		if(!sendText(userIdLocator, userId, logger)){
			throw new Exception("Failed to Enter User Id");
		}
	}

	public void enterPassword(String password) throws Exception {
		if(!sendText(passwordLocator, password, logger)){
			throw new Exception("Failed to Enter User Id");
		}
	}

	public void submitLogin() throws Exception {
		if(!clickByLocator(submitButton, logger)){
			throw new Exception("Failed to Submit Login");
		}
	}

	public HomePage loginUser(String userName, String password) {
		try {
			enterUserId(userName);
			enterPassword(password);
			submitLogin();
			return PageFactory.initElements(webDriver, HomePage.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception Occurred: "+e.getMessage());
			return null;
		}
	}
}
