package com.myCompany.util;

import org.openqa.selenium.ElementNotVisibleException;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class ExceptionHandler extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionHandler(String message) {
		super(message);		
	}
	
	public ExceptionHandler(Exception e) {
		super(e.getMessage());	
		ReportHandler.consolePrint("Customized Exception");
		ReportHandler.consolePrint(e.getMessage());
	}
	
	public ExceptionHandler(Exception e, String elementName) {		
		if(e instanceof ElementNotFoundException){
			ReportHandler.consolePrint("****** Element Not found   --> "+ elementName +"******");
		}if(e instanceof ElementNotVisibleException){
			ReportHandler.consolePrint("****** Element Not Visible   --> "+ elementName +"******");
		}
		else
			ReportHandler.consolePrint(e.getMessage());
	}
	
	
}
