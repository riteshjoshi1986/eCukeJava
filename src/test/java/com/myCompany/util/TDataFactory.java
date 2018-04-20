package com.myCompany.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.fluttercode.datafactory.impl.DataFactory;

import com.github.javafaker.Faker;

public class TDataFactory {
	
// -------> For POM File	
//	<dependency>
//    <groupId>org.fluttercode.datafactory</groupId>
//    <artifactId>datafactory</artifactId>
//    <version>0.8</version>
//    <type>jar</type>
//  </dependency>
	
// --- Used Faker for random test data
//<dependency>
//    <groupId>com.github.javafaker</groupId>
//    <artifactId>javafaker</artifactId>
//    <version>0.12</version>
//</dependency>

	
	
	static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static final String Num = "0123456789";
	
	static Random rnd = new Random();
	static DataFactory df = new DataFactory();
	
	public static String randomString(int len){
//	   StringBuilder sb = new StringBuilder(len);
//	   for(int i = 0; i < len; i++) 
//	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
//	   return sb.toString();
		return df.getRandomWord(len, true);
	}
	
	public static long randomNumber(int len){
		String sb ="";
		try {			
			   for(int i = 0; i < len; i++) 
			      sb=sb + rnd.nextInt(10);
			   return Long.valueOf(sb); 
		}catch(Exception e) {
			new ExceptionHandler(e);
			return 0;
		}
		
	}
	
	public static String getRandomPhoneNumber() { 
//		return new Faker().phoneNumber().toString();
		String phone = "924" + randomNumber(3) + randomNumber(4);
		if(phone.length()<10)
			phone = phone + "1";
		ReportHandler.consolePrint("Phone --> " + phone);
		return phone; 
	}
	
	public static String getEmail() {
		return "qatest_" + new Faker().internet().emailAddress();
		//return df.getRandomChars(3,8)+randomNumber(3) + "@" + df.getRandomChars(3,8) + ".com"; 
	}
	
	public static String getAddressLine1() {
		return df.getAddress();
	}
	
	public static String getCity() {
		return df.getCity();
	}

	public static String getState() {
		return "VA";
	}
	
	public static String getZipCode() {
		return "28116";
	}
	
	public static String getFirstName() {
		//return df.getFirstName();		
		//return randomString(8);
		return new Faker().name().firstName();
		//return df.getRandomChars(3, 8);
	}
	
	public static String getLastName() {
		return new Faker().name().lastName();
		//return df.getRandomChars(3, 8);
		//return df.getLastName();
	}
	
	public static String getRandomZipCode() {
		//return new Faker().address().zipCode();
		return "75023";
	}
	
	public static String getDateTimeStamp() {
		return new SimpleDateFormat("MMddHHmmssSSS").format(new Date());
	}
	
	public static String getDateMMddYYY(){
		return getDateMMddYYY(0);		
	}
	
	public static String getDateMMddYYY(int days){
		return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
		// return new SimpleDateFormat("MM/dd/YYYY" + days).format(new Date());		
	}

	public static String getDate(String format, int days){
		return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(format));
	}
	
	public Map<String,String> defaultData() {
		Map<String,String> defaultData=new HashMap<String, String>();
		
		// Test Data --> First Name
		defaultData.put("first name", getFirstName());
		
		// Test Data --> Last name 
		defaultData.put("last name", getLastName());
		
		// Test Data --> Email			
		defaultData.put("email", getEmail());
		
		// Test Data --> Phone
		defaultData.put("phone", getRandomPhoneNumber());
		
		// Test Data --> Zip Code
		defaultData.put("zip", getRandomZipCode());
		
		return defaultData;
	}
	
// 		Commented line is used to consider csv header
//		FILE_HEADER = {"First Name","Last Name","Status","Borrowers Home Phone","Channel","Channel Sources","Channel Values","Property State","Loan Purpose","Borrowers Zip Code","Requested Loan Amount","Credit Score","Property Zip Code"};
	
	public static void createCSVTestDataFile(List<ArrayList<String>> data, String filePath) {
		
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
		try {
			
			//initialize FileWriter object
			fileWriter = new FileWriter(filePath);
			
			//initialize CSVPrinter object 
	        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
	        
	        //Create CSV file header
	        csvFilePrinter.printRecords(data);
			
			System.out.println("CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			new ExceptionHandler(e);
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (Exception e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                new ExceptionHandler(e);
			}
			
		}
		
	}
	
}
