package com.myCompany.util;

import java.util.HashMap;
import java.util.Map;

/**
 * CSV Data Load Template
 */
public class CSVDataLoadTemplate {
	
	final String NEW_LINE_SEPARATOR = "\n";
	final String[] FILE_HEADER = {"First Name","Last Name","Status","Borrowers Home Phone","Channel","Channel Sources","Channel Values","Property State","Loan Purpose","Borrowers Zip Code","Requested Loan Amount","Credit Score","Property Zip Code"};
	final String[] FILE_RECORD = {"L1","L1","New - Not Opened","401","Centralized Retail","Web","Lead Aggregator","TX","Purchase","","100000","700","70425"};
	
	
	public static Map<String,String> createCSVPrequalLead() {
		// Test Data is @In FP Zip PQ - NO1
		Map<String,String> data = new HashMap<String,String>();
		data.put("First Name", "firstName");
		data.put("Last Name","lastName");
		data.put("Status","New - Not Opened");
		data.put("Borrowers Home Phone","7041231234");
		data.put("Email","p@p.com");
		data.put("Channel","Centralized Retail");
		data.put("Channel Sources","Web");
		data.put("Channel Values","Lead Aggregator");
		data.put("Property State","TX");
		data.put("Loan Purpose","Purchase");
//		data.put("Borrowers Zip Code","");
		data.put("Requested Loan Amount","100000");
		data.put("Credit Score","700");
		data.put("Property Zip/ Postal Code","70425");
		data.put("Occupancy Code", "Primary Residence");
		data.put("Loan Type", "Conventional");
		return data;
	}

	public static Map<String,String> createCSVBankLead() {
		// Test Data is @In FP Zip PQ - NO1
		Map<String,String> data = new HashMap<String, String>();
		data.put("First Name", "firstName");
		data.put("Last Name","lastName");
		data.put("Status","New - Not Opened");
		data.put("Borrowers Home Phone","7041231234");
		data.put("Channel","Centralized Retail");
		data.put("Channel Sources","Bank");
		data.put("Channel Values","RB SOFI");
		data.put("Property State","TX");
		data.put("Loan Purpose","Purchase");
		data.put("RB Cost Center","46705");
		data.put("RB Branch","176 Broadway");
		data.put("RB Email","email");
		return data;
	}
	
	public static Map<String,String> createCSVUpdateOpportunity() {
		Map<String,String> data = new HashMap<String, String>();
		data.put("ID", "XXXXXXX");
		data.put("OWNERID","YYYYYYYYY");
		return data;
	}
}
