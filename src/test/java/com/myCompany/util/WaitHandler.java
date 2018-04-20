package com.myCompany.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.myCompany.myProject.pages.Page;

public class WaitHandler {
	final static int waitTimeSeconds = 90;
	final static int waitTimeMilliSeconds = 9500;
	final static int waitSmallTimeMilliSeconds = 3000;
	
	public static WebDriverWait getWebDriverWait(WebDriver driver) {
		return new WebDriverWait(driver, waitTimeSeconds);
	}
	
	public static void staticWait() {
		try {
			Thread.sleep(waitTimeMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void staticSmallWait() {
		try {
			Thread.sleep(waitSmallTimeMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void waitForElementToPresent(String[] element){
		try {
			new Page().waitForElementVisible(element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
