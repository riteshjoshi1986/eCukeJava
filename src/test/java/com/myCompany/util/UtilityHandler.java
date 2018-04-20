package com.myCompany.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.myCompany.myProject.pages.Page;

public class UtilityHandler {

			public static String isLinkBroken(URL url) throws Exception
	
			{
	
				String response = "";
	
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	
				try
	
				{
	
				    connection.connect();
	
				     response = connection.getResponseMessage();	        
	
				    connection.disconnect();
	
				    return response;
	
				}
	
				catch(Exception exp)
	
				{
	
					return exp.getMessage();
	
				}  				
	
			}
	
			public static void validateLinksOnPage(String url) {
					
					Page page = new Page();
					page.navigate_to(url);
					List<WebElement> allImages = page.findAllLinks();	
							    
				    System.out.println("Total number of elements found " + allImages.size());
	
				    for( WebElement element : allImages){
	
				    	try
	
				    	{
	
					        System.out.println("URL: " + element.getAttribute("href")+ " returned " + isLinkBroken(new URL(element.getAttribute("href"))));
	
				    		System.out.println("URL: " + element.getAttribute("outerhtml")+ " returned " + isLinkBroken(new URL(element.getAttribute("href"))));
	
				    	}
	
				    	catch(Exception exp)
	
				    	{
	
				    		System.out.println("At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());	    		
	
				    	}
	
				    }
	
			    }
	
	
	
	
	
	
	
	
	
	
	
	//******************************************************************
	
	
		public static String getFilePathUsingRegualarExpression(String fileExpression,String path)
		{
		    File directory = new File(path);
			File[] files = directory.listFiles();

	// ******************* Debug Code
//			System.out.println("All files and directories:");
//			for (File file : files) {
//				System.out.println(file.getName());
//			}
	// *******************
			
			String pattern = fileExpression;
			System.out.println("\nFiles that match regular expression: " + pattern);
			FileFilter filter = new PrefixFileFilter(pattern,IOCase.INSENSITIVE);
			files = directory.listFiles(filter);

	// ******************* Debug Code
//			for(File file: files) {
//				System.out.println("Filter Files: " + file.getName());			
//			}
	// *******************		
			
			return 	files[0].toString();
		}
		
		public static boolean validateRowCountInCSV(int expectedCount,String filePath)
		{   
		    
		    try {	  
		    	
		    	
		    	BufferedReader br = null;
		    	String line = "";
		    	String cvsSplitBy = ",";
		    	String[] records = null;
		    	
		    	br = new BufferedReader(new FileReader(filePath));
				while ((line = br.readLine()) != null) {
		 
				        // use comma as separator
					records = line.split(cvsSplitBy);
					}	        
		        
		        System.out.println("Rows Count" + records.length);
		        if (expectedCount == records.length)
		        	System.out.println("Pass");
		        else
		        	System.out.println("Fail");
		        br.close();	        
		        
		        return true;
		    } catch (IOException e) {
		        e.printStackTrace();
		    } 
			return false;
		}
		
		public static boolean validateRowCountInExcel(int expectedCount,String excelPath)
		{   
		    FileInputStream fis = null;
		    try {
		        //
		        // Create a FileInputStream that will be use to read the excel file.
		        //
		    	
		        fis = new FileInputStream(excelPath);
		        //
		        // Create an excel workbook from the file system.
		        //
		        HSSFWorkbook workbook = new HSSFWorkbook(fis);
		        
		        //
		        // Get the first sheet on the workbook.
		        //
		        HSSFSheet sheet = workbook.getSheetAt(0);
		
		        //
		        // When we have a sheet object in hand we can iterator on each
		        // sheet's rows and on each row's cells. We store the data read
		        // on an ArrayList so that we can printed the content of the excel
		        // to the console.
		        //
		        Iterator<Row> rows = sheet.rowIterator();
		        int count =0;
		        while (rows.hasNext()) {
		        	++count;
		        }
		        System.out.println("Rows Count" + count);
		        if (expectedCount == count)
		        	System.out.println("Pass");
		        else
		        	System.out.println("Fail");
		        	workbook.close();
		        fis.close();
		//        java.io.File file = null;
		        return true;
		    } catch (IOException e) {
		        e.printStackTrace();
		    } 
			return false;
		}
}

// *********************************************************************************
//     To check for broken links on the page while executing Selenium WebDriver tests, we can use:
// *********************************************************************************

//import com.jayway.restassured.response.Response;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//
//import java.util.List;
//
//import static com.jayway.restassured.RestAssured.given;
//
//public class HttpResponseCode {
//
//    WebDriver driver;
//    Response response;
//
//    public void checkBrokenLinks() {
//        driver = new FirefoxDriver();
//        driver.get("http://www.testingexcellence.com");
//
//        //Get all the links on the page
//        List<WebElement> links = driver.findElements(By.cssSelector("a"));
//
//        String href;
//        
//        for(WebElement link : links) {
//            href = link.getAttribute("href");
//            response = given().get(href).then().extract().response();
//
//            if(200 != response.getStatusCode()) {
//                System.out.println(href + " gave a response code of " + response.getStatusCode());
//            }
//        }
//    }
//
//    public static void main(String args[]) {
//        new HttpResponseCode().checkBrokenLinks();
//    }
//}
