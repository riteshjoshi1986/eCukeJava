package com.myCompany.myProject.pages;

public class GlobalNavigation extends HomePage {
	
	public void globalNavigation(String linkText) {
		click_by_text(linkText);
	}
	
	public GlobalNavigation openRecord(String recordName) {
		globalSearch(recordName);
		click_by_text(recordName);
		return this;
	}
	
}
