package com.myCompany.eCuke;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
//Adding comments to test git functionality
@CucumberOptions(
		features = {"src/test/resources/com/myCompany/myProject/features/test.feature"},
		//tags = {"@web","@wip","~@setup","~@regression","~@obsolete"}, // --> WIP feature
		tags = {"@web"},
		glue = { "com.myCompany.myProject","com.myCompany.util" },
		plugin = {"pretty","html:target/cucumber-html-report-ECuke/","json:target/cucumber-json-report/cucumberReport_Batch1.json"}
		)
@RunWith(Cucumber.class)
public class ECukeRunner{}
