package com.myCompany.myProject.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.myCompany.util.ExceptionHandler;
import com.myCompany.util.Hooks;
import com.myCompany.util.ReportHandler;
import com.myCompany.util.SetUp;
import com.myCompany.util.WaitHandler;

public class Page {
		public WebDriver driver = Hooks.browser;
		protected final String ID = "id";
		protected final String NAME = "name";
		protected final String CLASS = "class";
		protected final String CSS_SELECTOR = "css";
		protected final String LINK_TEXT = "linktext";
		protected final String PARTIAL_LINK_TEXT = "partiallinktext";
		protected final String TAG_NAME = "tagname";
		protected final String XPATH = "xpath";
			
		/**
		 * 
		 * @param locator
		 * locator can be id,name,class,css,linktext,partiallinktext,tagname,xpath
		 * @param identifier
		 * @return
		 */
		
		private By getByElement(String locator,String identifier) {
			try{
				By by=null;
				switch (locator.toLowerCase()) {
					
					case "id":
						by = By.id(identifier);
						break;
						
					case "name":
						by = By.name(identifier);
						break;
						
					case "class":
						by = By.className(identifier);
						break;
						
					case "css":
						by = By.cssSelector(identifier);
						break;
						
					case "linktext":
						by = By.linkText(identifier);
						break;
						
					case "partiallinktext":
						by = By.partialLinkText(identifier);
						break;
						
					case "tagname":
						by = By.tagName(identifier);
						break;
						
					case "xpath":
						by = By.xpath(identifier);
						break;
						
				}
				return by;
				
			}catch(NoSuchElementException e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
		
		protected WebElement getElementByIndex(String locator,String identifier,int index) {
			setFocus(driver, locator, identifier);			
			return getElementByIndex(driver, locator, identifier, index);
		}
		
		protected WebElement getElementByIndex_NoDelay(String locator,String identifier,int index) {
			//setFocus(driver, locator, identifier);			
			return getElementByIndex_NoDelay(driver, locator, identifier, index);
		}
		
		private WebElement getElementByIndex(WebDriver driver,String locator,String identifier,int index) {
			waitForElementVisible(driver, locator, identifier);
			return driver.findElements(getByElement(locator, identifier)).get(index);
		}
		
		private WebElement getElementByIndex_NoDelay(WebDriver driver,String locator,String identifier,int index) {
			return driver.findElements(getByElement(locator, identifier)).get(index);
		}
		
//		public WebElement element(String[] locator) {
//			waitForElementVisible(driver, locator[0], locator[1]);
//			if (locator.length >2) {				
//				return driver.findElements(getByElement(locator[0], locator[1])).get(Integer.valueOf(locator[2]));	
//			}else{
//				return driver.findElements(getByElement(locator[0], locator[1])).get(0);	
//			}			
//		}
		
		private WebElement getLastElement(String locator,String identifier) {
			waitForElementVisible(driver, locator, identifier);
			int index = getElements(locator, identifier).size();
			return getElementByIndex(locator, identifier, index-1);
		}		
		
		protected WebElement getElement(String locator, String identifier) {
			return getElement(driver, locator, identifier);
		}
		
		protected WebElement getElement(String[] locator) {
			return getElement(driver, locator[0], locator[1]);
		}
		
		private WebElement getElement(WebDriver driver,String locator, String identifier) {
			waitForElementVisible(driver, locator, identifier);
			return getElementByIndex(driver, locator, identifier,0);
		}
		
		protected List<WebElement> getElements(String locator,String identifier) {
			return driver.findElements(getByElement(locator, identifier));			
		}
		
		protected List<WebElement> getElements(String[] locator) {
			return driver.findElements(getByElement(locator[0], locator[1]));			
		}
			
		/**
		 * 
		 * @param url
		 * navigate to specific url
		 */
		public void navigate_to(String url){
			driver.get(url);
			if(isAlertDisplays()==true)
				new Page().acceptAlertPopup();
		}
		
		public void navigateBack() {
			driver.navigate().back();
		}
		
		public void quitBrowser() {
			driver.quit();
		}
		
		public void closeBrowser() {
			driver.close();
		}
		
		/**
		 * 
		 * @param title
		 * Get the title of the current page 
		 * @return String
		 */
		public String getPageTitle(){
			return driver.getTitle();
		}
		
		
		/**
		 * Get the Current URL of web page
		 */
		public String getCurrentURL() {
			return driver.getCurrentUrl();
		}
		
		/**
		 * 
		 * @param title
		 * Verify the title of the current page 
		 * @return
		 */
		public boolean isPageTitleMatches(String title){
			return driver.getTitle().contains(title);
		}
		
		public String getCurrentFrameName(){
			JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
			String currentFrame = (String) jsExecutor.executeScript("return self.name");
			return currentFrame;
		}
				
		/**
		 * Is Text Present in Page source
		 * @return 
		 */
		public boolean isTextPresentOnPage(String text) {
			return driver.getPageSource().contains(text);
		}
		
		/**
		 * 
		 * @param text -- Search-string
		 * @param index -- position from where search start
		 * @return
		 */
		public boolean isTextPresentOnPage(String text, String startString) {			
			String searchArea = driver.getPageSource().substring(driver.getPageSource().indexOf(startString));   
			return searchArea.contains(text);
		}
		
		/**
		 * 
		 * @param text -- Search-string
		 * @param startString -- position from where search start
		 * @param endString -- position from where search stops
		 * @return
		 */
		public boolean isTextPresentOnPage(String text, String startString, String endString) {	
			WaitHandler.staticWait();
			String pageSource=driver.getPageSource();
			String searchArea = pageSource.substring(pageSource.indexOf(startString),pageSource.indexOf(endString));
			return searchArea.contains(text);
		}
		
		public boolean isTextPresentOnPage(String text, String startString, int endPosition) {			
			String pageSource=driver.getPageSource();
			String searchArea = pageSource.substring(pageSource.indexOf(startString),pageSource.indexOf(startString) + endPosition);
			return searchArea.contains(text);
		}
		
		/**
		 * Get Page Source
		 * @return pageSource
		 */
		public String getPageSource() {
			return driver.getPageSource();
		}
		
		// Text Matches
		public boolean isTextMatchesPattern(String text,String pattern) {
			Pattern p = Pattern.compile(pattern);
			return p.matcher(text).lookingAt();
		}
		
		/**
		 * Navigate to default url
		 */
		public void navigate_to() {
			SetUp.properties("com/myCompany/myProject/data/data.properties");
			navigate_to(SetUp.getPropertyValue("salesforce_url"));
		}
		
		public void deleteCookies() {
			driver.manage().deleteAllCookies();
			WaitHandler.staticSmallWait();
		}
		
		/**
		 * 
		 * @param locator
		 * can be id,name,class,css,linktext,partiallinktext,tagname,xpath
		 * @param identifier
		 * @param text
		 * value need to be provided
		 */
		public void setText(String locator,String identifier, String text){
//			waitForElementVisible(locator, identifier);
//			clearTextField(locator, identifier);
//			getElement(locator, identifier).sendKeys(text);
			setText(driver,locator,identifier,text);
		}
		
		public void setText(String[] locator, String text){
			setText(driver,locator[0],locator[1],text);
		}
		
		public void setText(WebDriver driver, String locator,String identifier, String text){
			waitForElementVisible(driver, locator, identifier);
			setFocus(locator, identifier);
			clearTextField(driver, locator, identifier);			
			getElement(driver, locator, identifier).sendKeys(text);
			getElement(driver,locator,identifier).sendKeys(Keys.TAB);
		}
		
		/**
		 * 
		 * @param locator
		 * can be id,name,class,css,linktext,partiallinktext,tagname,xpath
		 * @param identifier
		 * @param text
		 * value need to be provided
		 */
		public void clearAndSetText(String locator,String identifier, String text){
			waitForElementVisible(locator, identifier);
			clearTextField(locator, identifier);
			getElement(locator, identifier).sendKeys(text);
		}
		
		/**
		 * 
		 * @param locator
		 * can be id,name,class,css,linktext,partiallinktext,tagname,xpath
		 * @param identifier
		 * @param text
		 * value need to be provided
		 */
		public void clearTextField(String locator,String identifier){
//			waitForElementVisible(locator, identifier);
//			getElement(locator, identifier).clear();
			clearTextField(driver, locator, identifier);
		}
		
		public void clearTextField(WebDriver driver, String locator,String identifier){
			waitForElementVisible(driver, locator, identifier);
			getElement(driver, locator, identifier).clear();
		}
		
		
		public void selectOption(String[] locator, String text) {
			selectOption(locator[0], locator[1], text);
		}
		
		/**
		 * @param locator
		 * can be id,name,class,css,linktext,partiallinktext,tagname,xpath
		 * @param identifier
		 * @param option
		 * value need to be provided
		 */
		public void selectOption(String locator, String identifier, String text) {
			waitForElementVisible(locator, identifier);
			List<String> options = getOptionList(locator, identifier);
			int index = options.indexOf(text);
			if (index>=0) {
				selectOptionByIndex(locator, identifier, index);
				WaitHandler.staticSmallWait();
			}
				
			
			//new Select(getElement(locator, identifier)).selectByValue(text);
			// select value is dynamic
		}
		
		public void selectOptionByValue(String locator, String identifier, String text) {
			waitForElementVisible(locator, identifier);
			new Select(getElement(locator, identifier)).selectByValue(text);
		}
		
		public void selectOptionByIndex(String locator, String identifier, int i) {
			waitForElementVisible(locator, identifier);
			new Select(getElement(locator, identifier)).selectByIndex(i);
		}
		
		public void selectSecondOption(String locator, String identifier) {
			selectOptionByIndex(locator, identifier, 1);
		}
		
		public void selectFirstOption(String locator, String identifier) {
			selectOptionByIndex(locator, identifier, 0);
		}
		
		public void selectFirstOption(String[] locator) {
			selectOptionByIndex(locator[0], locator[1], 0);
		}
		
		public List<WebElement> getOptions(String locator,String identifier) {
			waitForElementVisible(locator, identifier);
			return new Select(getElement(locator, identifier)).getOptions();
		}
		
		public List<String> getOptionList(String locator,String identifier) {
			waitForElementVisible(locator, identifier);
			List<WebElement> options = new Select(getElement(locator, identifier)).getOptions();
			List<String> values=new ArrayList<String>();
			for (WebElement option : options)
				 values.add(option.getText());
			return values;
		}
		
		public void selectLastOption(String locator, String identifier) {
			selectOptionByIndex(locator, identifier, getOptions(locator, identifier).size()-1);
		}
		
		public void selectLastOption(String[] locator) {
			selectLastOption(locator[0], locator[1]);
		}
		
		public void clickElement(String[] locator) {
			clickElement(locator[0],locator[1],0);
		}
		
		/**
		 * Click Element
		 * @param element
		 */
		public void clickElement(String locator,String identifier) {
			clickElement(locator,identifier,0);
		}
		
		public void clickElement(String locator,String identifier,int index) {
			waitForElementVisible(locator, identifier);
			getElementByIndex(locator,identifier,index).click();
		}
		
		public void clickAndWaitElement(String locator,String identifier,int x, int y) {
			waitForElementVisible(locator, identifier);
			WebElement ele = getElementByIndex(locator,identifier,0);
			Actions act = new Actions(driver);
			act.moveToElement(ele, 0, 0).moveByOffset(x, y).click().perform();
			WaitHandler.staticSmallWait();
		}
		
		public void clickLastElement(String locator,String identifier) {
			waitForElementVisible(locator, identifier);
			getLastElement(locator,identifier).click();
		}
		
		public void clickAndWaitElement(String locator,String identifier,int index) {
			clickElement(locator, identifier,index);
			WaitHandler.staticSmallWait();
		}
		
		public void clickAndWaitLastElement(String locator,String identifier) {
			clickLastElement(locator, identifier);
			WaitHandler.staticSmallWait();
		}
		
		public void clickAndWaitElement(String locator,String identifier) {
			clickAndWaitElement(locator,identifier,0);
		}
		
		public void clickAndWaitElement(String[] locator ) {
			if(locator.length < 3)
				clickAndWaitElement(locator[0],locator[1],0);
			else if (locator.length < 4)
				clickAndWaitElement(locator[0],locator[1],Integer.parseInt(locator[2]));
			else
				clickAndWaitElement(locator[0],locator[1],Integer.parseInt(locator[2]),Integer.parseInt(locator[3]));
		}
		
		/**
		 * Click link by text
		 * @param linkText
		 */
		public void click_by_text(String linkText){
			click_by_text(linkText, 0);
		}
		
		/**
		 * Click link by text
		 * @param linkText
		 * @param index
		 */
		public void click_by_text(String linkText,int index){
			clickAndWaitElement(LINK_TEXT, linkText, index);
		}
		
		public void clickLastElement_by_text(String linkText){
			clickAndWaitLastElement(LINK_TEXT, linkText);
		}
		
		
		/**
		 * Click link by text
		 * @param linkText
		 */
		public void click_by_partialText(String partialLinkText){
			click_by_partialText(partialLinkText, 0);			
		}
	
		/**
		 * Click link by text
		 * @param linkText
		 * @param index
		 */
		public void click_by_partialText(String partialLinkText,int index){
			clickAndWaitElement(PARTIAL_LINK_TEXT, partialLinkText,index);
		}
		
		/**
		 * Set the focus on the element 
		 * @param locator
		 * @param elementName
		 */
		public void setFocus(String locator,String elementName) {
			try{
				WebElement element = getElement(locator, elementName);
				setFocus(element);
			}catch(NoSuchElementException e1) {
				System.out.println("Element Not Found");
			}
			
		}
		
		public void setFocus(String[] locator){
			WebElement ele = getElement(locator);
			setFocus(ele);
		}
		
		public void setFocus(WebDriver driver, String locator,String elementName) {
//			try{
//				WebElement element = getElement(locator, elementName);
//				new Actions(driver).moveToElement(element).perform();
//			}catch(NoSuchElementException e1) {
//				System.out.println("Element Not Found");
//			}
			
		}
		
		public void setFocus(WebElement element) {
			try{
				if(element.getTagName().toLowerCase().contains("input"))
					element.sendKeys("");
				else
					new Actions(driver).moveToElement(element).perform();
			}catch(NoSuchElementException e1) {
				System.out.println("Element Not Found");
			}
			
		}
		
		public boolean isExists_NoDelay(String locator,String elementName) {
			return isExists_NoDelay(locator, elementName,"0");
		}
		
		/**
		 * Is element exists
		 * @param locator
		 * @param elementName
		 * @return true or false
		 */
		public boolean isExists(String locator,String elementName) {
			return isExists(locator, elementName,"0");
//			try{
//				waitForElementVisible(locator, elementName);				
//				if (getElement(locator, elementName).isDisplayed() == true)
//				{
//					setFocus(locator, elementName);
//					return true;
//				}
//				return false;
//			}catch(ElementNotFoundException e) {
//				System.out.println("Element Not Found " + e.getMessage());
//			}catch(NoSuchElementException e1) {
//				System.out.println("Element Not Found");
//			}catch(TimeoutException e) {
//				System.out.println("Element Not Found " + e.getMessage());
//			}
//			return false;
		}
		
		/**
		 * Is element exists
		 * @param locator
		 * @param elementName
		 * @return true or false
		 */
		public boolean isExists(String locator,String elementName, String index) {
			try{
				//waitForElementVisible(locator, elementName);				
				if (getElementByIndex(locator, elementName, Integer.valueOf(index)).isDisplayed() == true)
				{
					setFocus(locator, elementName);
					return true;
				}
				return false;
			}catch(Exception e) {
				new ExceptionHandler(e, elementName);
			}
//			}catch(ElementNotFoundException e) {
//				System.out.println("Element Not Found " + e.getMessage());
//			}catch(NoSuchElementException e1) {
//				System.out.println("Element Not Found");
//			}catch(TimeoutException e) {
//				System.out.println("Element Not Found " + e.getMessage());
//			}
			return false;
		}
		
		/**
		 * Is element exists
		 * @param locator
		 * @param elementName
		 * @return true or false
		 */
		public boolean isExists_NoDelay(String locator,String elementName, String index) {
			try{
				if (getElementByIndex_NoDelay(locator, elementName, Integer.valueOf(index)).isDisplayed() == true)
				{
					//setFocus(locator, elementName);
					return true;
				}
				return false;
			}catch(Exception e) {
				new ExceptionHandler(e, elementName);
			}
			return false;
		}
		
		public boolean isExists_NoDelay(String[] locator) {
			if(locator.length < 3)
				return isExists_NoDelay(locator[0],locator[1],"0");
			else
				return isExists_NoDelay(locator[0],locator[1],locator[2]);
		}
		
		public boolean isExists(String[] locator) {
			if(locator.length < 3)
				return isExists(locator[0],locator[1]);
			else
				return isExists(locator[0],locator[1],locator[2]);
		}
		
		public boolean isEnabled(String locator,String elementName){
			waitForElementVisible(locator, elementName);
			return getElement(locator, elementName).isEnabled();
		}
		
		public boolean isEnabled(String[] locator){
			return isEnabled(locator[0], locator[1]);
		}
		
		public void updateImplicitWait(int waitSeconds)
		{
			 driver.manage().timeouts().implicitlyWait(waitSeconds, TimeUnit.SECONDS);
		}
		
		public boolean isNotExists(String[] locator){
			return isNotExists(locator[0], locator[1]);
		}
		
		/**
		 * Is element exists
		 * @param locator
		 * @param elementName
		 * @return true or false
		 */
		public boolean isNotExists(String locator,String elementName) {
			updateImplicitWait(4);
			try{	
				driver.findElement(getByElement(locator, elementName)).isDisplayed();
				//getElement(locator, elementName).isDisplayed();
				return false;
				
//				if (getElement(locator, elementName).isDisplayed() == true)
//				{
//					setFocus(locator, elementName);
//					return false;
//				}else
//					return true;
			}catch(ElementNotFoundException e) {
				System.out.println("Element Not Found " + e.getMessage());
				return true;
			}catch(NoSuchElementException e1) {
				System.out.println("Element Not Found");
				return true;
			}catch(TimeoutException e) {
				System.out.println("Element Not Found " + e.getMessage());
				return true;
			}
			finally{
				updateImplicitWait(30);
			}
		}
		
		/**
		 * 
		 * @param linkText
		 * @return
		 */
		public boolean isLinkExists(String linkText) {
			return isExists_NoDelay(new String[] {LINK_TEXT,linkText});
			
//			try{
//				WebElement element = getElement(LINK_TEXT,linkText);
//				waitForElementVisible(LINK_TEXT, linkText);
//				if (element.isDisplayed() == true)
//				{
//					setFocus(element);
//					return true;
//				}
//				return false;
//
//			}catch(ElementNotFoundException e) {
//				System.out.println("Element Not Found " + e.getMessage());
//				return false;
//			}catch(NoSuchElementException e1) {
//				System.out.println("Element Not Found");
//				return false;
//			}catch(TimeoutException e) {
//				System.out.println("Element Not Found " + e.getMessage());
//				return false;
//			}
		}
		
		
		/**
		 * Get element text by attribute
		 * @param tag
		 * @param attribute
		 * @return text 
		 */
		
		public String getElementTextByAttribute(String tag, String attribute){			
			return getElement(TAG_NAME,tag).getAttribute(attribute);
		}
		
		public String getAlertText() {
			waitForAlert();
//			driver.switchTo().window("Alert").getPageSource();
			return driver.switchTo().alert().getText();
			
		}
		
		public void acceptAlertPopup() {
			driver.switchTo().alert().accept();
		}
		
		public void refreshBrowser() {
			driver.navigate().refresh();
		}
		
		public void waitForElementVisible(String locator, String elementName) {
			waitForElementVisible(driver,locator,elementName);
		}
		
		public void waitForElementVisible(String[] locator) {
			waitForElementVisible(driver,locator[0],locator[1]);
		}
		
		public void waitForElementVisible(WebDriver driver, String locator, String elementName) {
			try{
			WaitHandler.getWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(getByElement(locator, elementName)));
			}catch(Exception e){
				new ExceptionHandler(e,elementName);
			}
		}
		
		public void waitForAlert() {
			WaitHandler.getWebDriverWait(driver).until(ExpectedConditions.alertIsPresent());
		}
		
		public boolean isAlertDisplays() {
			try {
		        driver.switchTo().alert();
		        return true;
		    }
		    catch (Exception e) {
		        return false;
		    }
			
		}
		
		public String getElementText(String[] locator) {
			return getElementText(locator[0], locator[1],0);
		}
		
		public String getElementText(String locator,String element) {
			return getElementText(locator, element,0);
		}
		
		public String getElementText(String locator,String identifier,int index) {
			waitForElementVisible(locator, identifier);
			setFocus(locator, identifier);
			return getElementByIndex(locator, identifier, index).getText();			
		}
		
		public int getElementIndexByText(String locator,String identifier,String text) {
			int index=0;
			waitForElementVisible(locator, identifier);
			for(WebElement element: getElements(locator, identifier)) {
				if(element.getText().contains(text)){					
					break;
				}
				++index;
			}
			return index;
		}
		
		public String getLastElementText(String locator,String identifier) {
			waitForElementVisible(locator, identifier);
			int index = getElements(locator, identifier).size();
			return getElementByIndex(locator, identifier, index-1).getText();	
		}		
		
		public int getLinkCount(String identifier) {
			waitForElementVisible(LINK_TEXT, identifier);
			return getElements(LINK_TEXT, identifier).size();
		}
		
		public String getPropertyValueOfElement(String[] locator, String property) {
			waitForElementVisible(locator[0], locator[1]);
			return getElement(locator[0], locator[1]).getAttribute(property);
		}
		
		public String getPropertyValueOfElement(String elementLocator,String elementIdentifier, String property) {
			waitForElementVisible(elementLocator, elementIdentifier);
			return getElement(elementLocator, elementIdentifier).getAttribute(property);
		}
		
		public String getWindowHandle() {
			return driver.getWindowHandle();
		}
		
		public String getWindowHandle(String title) {
			Set<String> handles =driver.getWindowHandles();
			for(String popup: handles){				
				if(driver.switchTo().window(popup).getTitle().contains(title)){
					return popup;
				}			
			}	
			return null;
		}
		
		public String getWindowHandleWithEmptyTitle() {
			Set<String> handles =driver.getWindowHandles();
			for(String popup: handles){				
				if(driver.switchTo().window(popup).getTitle().equalsIgnoreCase("")){
					return popup;
				}			
			}	
			return null;
		}
		
		public WebDriver getFrameDriver(String frameID) {
			return driver.switchTo().frame(frameID);
		}
		
		public WebDriver getFrameDriver(String[] frameID) {
			return driver.switchTo().frame(getElement(frameID));
		}
		
		public void backToDefaultWindow() {
			driver.switchTo().defaultContent();
		}
		
		public WebDriver getFrameDriver(int frameID) {
			//*[@id="ext-gen126"]/span[2]
			//#ext-comp-1020
			WaitHandler.staticSmallWait();
			return driver.switchTo().frame(frameID);
		}
		
		public int getFramesCount() {
			int len = driver.findElements(By.xpath("//iframe")).size();
			ReportHandler.consolePrint("Number of frames -- " + len);
			
			return len;
		}
		
//		public WebDriver getFrameDriver(WebElement element) {
//			return driver.switchTo().frame(element);
//		}
		
		public String closePopUp(String title) {
			Set<String> handles =driver.getWindowHandles();
			for(String popup: handles){				
				if(driver.switchTo().window(popup).getTitle().contains(title)){
					driver.close();
				}			
			}	
			return null;
		}
		
		public int getObjectindex(String obj, String[] objs) {
			int index;
			for(index=0;index<objs.length;index++){
				if(objs[index].contains(obj.toLowerCase().trim()))
					break;
			}
			return index;
		}
		
		public void keyAlphNumericPress(CharSequence keyValue) {
			WaitHandler.staticSmallWait();
			Actions keyAction = new Actions(driver);
			keyAction.sendKeys(keyValue).perform();
			WaitHandler.staticSmallWait();
		}
		
		public List<WebElement> findAllLinks()

		  {
	
			  List<WebElement> elementList = new ArrayList<WebElement>();
	
			  elementList = driver.findElements(By.tagName("a"));
	
			  elementList.addAll(driver.findElements(By.tagName("img")));
	
			  List<WebElement> finalList = new ArrayList<WebElement>(); ;
	
			  for (WebElement element : elementList)
	
			  {
	
				  if(element.getAttribute("href") != null)
	
				  {
	
					  finalList.add(element);
	
				  }		  
	
			  }	
	
			  return finalList;
	
		  }
		
		
		
		
//		public List<String> getElementsTextsByAttribute(String tag, String attribute){
//			try{
//				List<WebElement> webElements = driver.findElements(By.tagName(tag));
//				 
//				 List<String> texts = new ArrayList<String>();
//				 for(WebElement webElement: webElements) {
//					 texts.add(webElement.getAttribute(tag));
//				 }
//				 return texts;
//			}catch(Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//			 
//		}
		
}
