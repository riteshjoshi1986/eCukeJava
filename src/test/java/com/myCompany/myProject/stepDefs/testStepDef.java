package com.myCompany.myProject.stepDefs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.myCompany.util.Hooks;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class testStepDef {
	public WebDriver driver = Hooks.browser;
	
	
	@Given("^I want to write a step with precondition$")
	public void i_want_to_write_a_step_with_precondition() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    driver.get("http://www.amazon.com");
	    driver.manage().window().maximize();
	    Thread.sleep(5000);
		System.out.println("Given executed");
	}

	@When("^I complete action$")
	public void i_complete_action() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("watch");
		System.out.println("When executed");
	}

	@Then("^I validate the outcomes$")
	public void i_validate_the_outcomes() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		driver.close();
		System.out.println("Then executed");
	}

	
}
