package com.myCompany.myProject.pages;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.UnhandledAlertException;

import com.myCompany.util.ReportHandler;
import com.myCompany.util.SetUp;
import com.myCompany.util.WaitHandler;

public class HomePage extends Login {
	private String or_name_btnNew="new";
	private String or_name_btnEdit="edit";
	private String or_ID_sidebarCreateNew="createNew";
	private String or_name_btnNewOpportunity="newOpp";
	private String or_name_btnGo="go";
	private String or_css_btnContinue = "#bottomButtonRow > input:nth-child(1)";  
	private String or_name_btnSave = "save";
	private String or_name_btnCancel="cancel";
	private String or_id_lnkSetup="setupLink";
	private String or_id_txtSearch="phSearchInput";
	private String or_id_btnSearch="phSearchButton";
	private String or_id_lnkGlobalNavDropdown = "globalHeaderNameMink";
	private String or_id_lnkUserNavDropdown = "userNavLabel";
	private String or_lnktxt_lnkHomeTab="Home";
	private String or_css_lnkAllTabs="#AllTab_Tab > a > img"; 
	private String or_lnktxt_lnkContacts = "Contacts";
	private String or_name_btnDelete="del";
	private String or_name_btnSendAnEmail="send_an_email";
	private final String[] btnNewNote={XPATH,"//input[@value='New Note']"};
	//private String or_id_iframePromotions="09D540000004D8z_066540000004OPw"; //sp2
	private String or_id_iframePromotions="09D13000000PGKZ_06613000000bGGb"; //mck
	
	private String or_lnktxt_lnkChange="[Change]";
	private String or_lnktxt_lnkAddOwner="newOwn";
	private String or_css_lnkSearchNavigationObject="a[id^='ext-gen']";
	private String[] lblFieldValue = {XPATH,"//td[contains(@class,'labelCol') and text()='valueToReplace']/following::td[1]"};
	
	public String getFieldValue(String fieldName){
		return getElementText(lblFieldValue[0],lblFieldValue[1].replaceAll("valueToReplace", fieldName));
	}
	
	public Boolean envCheck() {
		String[] temp = SetUp.getPropertyValue("username").split("com"); //SetUp.getPropertyValue("username")
		 return temp[(temp.length)-1].contains("qasprint");
	}
	
	public HomePage clickChangeUserLnk() {
		clickElement(LINK_TEXT, or_lnktxt_lnkChange,0);
		return this;
	}
	
	public boolean isSaveButtonExists() {
		return isExists(NAME, or_name_btnSave);
	}
	
	public HomePage clickEdit() {
		clickAndWaitElement(NAME, or_name_btnEdit);
		return this;
	}
	
	public HomePage setTransferOwner(String user) {
		setText(ID,or_lnktxt_lnkAddOwner, user);
		WaitHandler.staticSmallWait();
		return this;
	}
	
	public HomePage changeToUser(String user) {
		clickChangeUserLnk();
		setTransferOwner(user);
		save();
		WaitHandler.staticWait();
		return this;
	}
	
	public int getObjectindex(String obj) {
		String[] objs={"new leads","opportunity or pipeline","customer or account","people or user"};
		int index;
		for(index=0;index<objs.length;index++){
			if(objs[index].contains(obj.toLowerCase().trim()))
				break;
		}
		return index;
	}
	
	public HomePage clickFeedOptionlink(String feedOption) {
		if (isExists(LINK_TEXT, "Hide Feed"))
			;
		else
			click_by_text("Show Feed");
		
		click_by_text(feedOption);
		switch(feedOption.toLowerCase()) {
		case "promotions":
			getFrameDriver(or_id_iframePromotions);
			break;
		}
		
		return this;
	}
	
	public HomePage continueBtn() {
		clickAndWaitElement(CSS_SELECTOR, or_css_btnContinue);		
		return this;
	}
	
	public HomePage clickCreateNew() {
		clickAndWaitElement(ID, or_ID_sidebarCreateNew);		
		return this;
	}
	
	public HomePage cancel() {
		clickAndWaitElement(NAME, or_name_btnCancel);
		return this;
	}
	
	public HomePage save() {
		clickAndWaitElement(NAME, or_name_btnSave);
		return this;
	}
	
	public HomePage clickNewButton() {
		clickAndWaitElement(NAME, or_name_btnNew);		
		return this;
	}
	
	public HomePage clickNewNoteButton() {
		clickAndWaitElement(btnNewNote);
		return this;
	}
	
	public HomePage clickNewOpportunityButton() {
		clickAndWaitElement(NAME, or_name_btnNewOpportunity);
		return this;
	}
	
	public Boolean isNewOpportunityButtonExists() {
		return isExists(NAME, or_name_btnNewOpportunity);
	}
	
	public Boolean isNewButtonExists() {
		return isExists(NAME, or_name_btnNew);
	}
	
	public String getAlertByNew() {
		String txt="";
		try{
			clickNewOpportunityButton();
			txt = getAlertText();
			
		}catch(UnhandledAlertException e) {
			ReportHandler.writeTextInReport(e.getAlertText());
		}
		acceptAlertPopup();
		return txt;
	}
	
	public HomePage navigateToAllTabs() {
		clickAndWaitElement(CSS_SELECTOR,or_css_lnkAllTabs);
		return this;
	}

	
	public HomePage navigateToHomeTab() {
		clickAndWaitElement(LINK_TEXT, or_lnktxt_lnkHomeTab);		
		return this;
	}
	
	public Boolean isUserNavigationDropdownExists() {
		return isExists(ID, or_id_lnkUserNavDropdown);		
	}
	
	
	public HomePage setSearchTextbox(String text) {
		setText(ID, or_id_txtSearch, text);
		return this;
	}
	

	public HomePage clickSearchButton() {
		clickAndWaitElement(ID, or_id_btnSearch);
		return this;
	}
	
	public HomePage clickGlobalNavigationLnk() {
		clickElement(ID, or_id_lnkGlobalNavDropdown);
		return this;
	}
	
	public HomePage clickGoBtn() {
		clickElement(NAME, or_name_btnGo);
		return this;
	}
	
	public Boolean isDeleteButtonPresent() {
		return isExists(NAME,or_name_btnDelete);
	}
	
	public Boolean isSendAnEmailPresent() {
		return isExists(NAME, or_name_btnSendAnEmail);
	}
	
	public HomePage openCreatedRecord(String objectName,String recordName) throws Throwable {
		globalSearch(objectName, recordName);
		openSearchedRecord(objectName, recordName);
		return this;
	}
	
	public HomePage openSearchedRecord(String objectName,String recordName) {
		String[] objNames = {"account","customer","opportunity","contacts"};
		if(Arrays.asList(objNames).contains(objectName.toLowerCase())) {
			click_by_text("Account Name");
			click_by_text(recordName);
		}
		else if(objectName.toLowerCase().contains("user")) {
			clickLastElement_by_text(recordName);
			
		}
		else {
			click_by_text("Created Date");
			click_by_text("Created Date");
			click_by_text(recordName);
		}
		return this;
	}
	
	/**
	 * Global Search
	 * @param searchKeyword Enter the keyword
	 */
	public HomePage globalSearch(String searchKeyword) {
		setSearchTextbox(searchKeyword);
		clickSearchButton();
		return this;
	}
	
	public HomePage globalSearch(String object,String searchKeyword) {
		globalSearch(searchKeyword);
		int index = getObjectindex(object);

		switch(index) {
		case 3: // --> people or user
			clickAndWaitElement(CSS_SELECTOR, or_css_lnkSearchNavigationObject, getElementIndexByText(CSS_SELECTOR,or_css_lnkSearchNavigationObject,"People"));
			break;
		case 2: //--> customer or account
			clickAndWaitElement(CSS_SELECTOR, or_css_lnkSearchNavigationObject, getElementIndexByText(CSS_SELECTOR,or_css_lnkSearchNavigationObject,"Customers"));
			break;
			
		case 1: //--> opportunity or pipeline
			break;
		
		case 0:	//--> new leads
			clickAndWaitElement(CSS_SELECTOR, or_css_lnkSearchNavigationObject, getElementIndexByText(CSS_SELECTOR,or_css_lnkSearchNavigationObject,"New Leads"));
			break;
			
		default:
			ReportHandler.consolePrint("**************globalSearch Not Found");
			return null;
		}
		return this;
	}
	
	
	/**
	 * Validate isFieldIsAddedWithValues
	 * @param actualValues Existing Values in the Application
	 * @param newValues 
	 * @return
	 */
	public Boolean isFieldIsAddedWithValues(List<String> actualValues, List<String> newValues) {
		List<String> lst = actualValues;
		Iterator<String> itr = newValues.iterator();
		Iterator<String> itr1 = lst.iterator();
		Boolean flag = false;
		Boolean master_flag=false;
		while(itr.hasNext()) {
			String temp=itr.next();
			flag = false;			
			while(itr1.hasNext() && flag==false) {				
				flag = flag || temp.contains(itr1.next());
			}
			master_flag = master_flag || flag;
		}
		return master_flag;				
	}
	
}
