package com.myCompany.util;

import org.openqa.selenium.WebDriver;

import cucumber.api.*;
import cucumber.api.java.*;

public class Hooks {
	public static WebDriver browser;
	//public static boolean executionStartedFlag=false;
	public static boolean executionStartedFlag= Boolean.getBoolean(SetUp.getPropertyValue("reportFlag"));
	
	private static String propfolder = "com/myCompany/myProject/data/";
	private static String propFile_data = propfolder + "data.properties";
	
	@Before
	public void setup(Scenario scenario){
		SetUp.properties(propFile_data);
		ReportHandler.setupReport(scenario);
	}
	
	@Before
	public void executeReportJar() {
		if(executionStartedFlag==false) {
			//ReportHandler.executeReportJar();
			executionStartedFlag = true;
		}		
	}
	
	@Before("@web")
	public void setupWebdriver() {
		if (browser == null)
			browser = SetUp.init(SetUp.getPropertyValue("browser"), "-");
	}	
	
	@After({"~@wip","~@jmeter","@newBrowser"})
	public void tearDown() throws Exception {
		ReportHandler.consolePrint("============After All ================");
		SetUp.quit();
		browser=null;
	}
	
//	@Before({"@web","@newBrowser"})
//	public void setupWebdriver() {
//		if (browser == null)
//			browser = SetUp.init(SetUp.getPropertyValue("browser"), "-");
//	}	
	
//	@After({"~@wip","~@jmeter","@newBrowser"})
//	public void tearDown() throws Exception {
//		SetUp.quit();
//	}
	
	@After("@web")
	public void end(Scenario scenario) throws Exception {
		//ReportHandler.writeTextInReport("tear down");
		if(scenario.isFailed()){
			try{
				ReportHandler.takeScreenshot();
				ReportHandler.embedScreenshot();
			}catch(Exception e){System.out.println("Project error message --> " + e.getMessage());}
		}
	}
}