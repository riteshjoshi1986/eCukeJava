package com.myCompany.util;

import org.openqa.selenium.*;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.imageio.ImageIO;

import org.apache.bcel.util.ClassLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.runtime.junit.ExecutionUnitRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberScenario;

public class ReportHandler {
	
	// ExecutionUnitRunner - Implemented, CucumberTagStatement - ?, and GherkinModel.getName() - ?
	
	private static Scenario scenario;
//	private static String scenarioName ;
//	private static String executionRunner;
//	private static String background;
//	private static String feature;
	private static String scenarioStep;
	private static WebDriver browser = Hooks.browser;
	private ReportHandler() {}
	private static final Logger logger = Logger.getLogger(ReportHandler.class.getName());
	private static FileHandler filehandler = null;
	private static SimpleFormatter formatter= null;
	
	
	public static void setupReport(Scenario scenario) {
		try {
			ReportHandler.scenario = scenario;
			filehandler = new FileHandler("test.log");
			formatter = new SimpleFormatter(); 
			filehandler.setFormatter(formatter);		
			logger.addHandler(filehandler);
			getFeatureFileTagNames();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void writeTextInReport(String text) {
		scenario.write(text);
	}
	
	private static String getScenarioStep() {
		return scenarioStep;
	}
	
	public static void consolePrint(String text) {
		System.out.println(text);
	}
	
	public static void manualExecution(String status) {
		//logger.info("Verification Step-> " + getScenarioStep());
		if(status.toLowerCase().contains("pass"))
			Assert.assertTrue(true);
		else if(status.toLowerCase().contains("not"))
			throw new PendingException();
		else
			Assert.assertTrue(false);
	}
	
//	private static String getExecutionRunner() {
//		return executionRunner;
//	}
	
	private static void getFeatureFileTagNames() {		
		try {
				
//				System.out.println("In get Feature File Tag Names: ");
//				scenario.write("In get Feature File Tag Names: scenario.write");
				Field f = scenario.getClass().getDeclaredField("reporter");
	            f.setAccessible(true);
	            
				JUnitReporter reporter = (JUnitReporter) f.get(scenario);
	 		    Field executionRunnerField = reporter.getClass().getDeclaredField("executionUnitRunner");
	            executionRunnerField.setAccessible(true);
	            ExecutionUnitRunner executionUnitRunner = (ExecutionUnitRunner) executionRunnerField.get(reporter);
//	            executionRunner = executionUnitRunner.getName();
	 
	            Field cucumberScenarioField = executionUnitRunner.getClass().getDeclaredField("cucumberScenario");
	            cucumberScenarioField.setAccessible(true);
	            CucumberScenario cucumberScenario = (CucumberScenario) cucumberScenarioField.get(executionUnitRunner);
//	            scenarioName = cucumberScenario.getVisualName();
	            
	            scenarioStep = cucumberScenario.getSteps().get(0).getName();

//            	Field cucumberBackgroundField = cucumberScenario.getClass().getDeclaredField("cucumberBackground");
//	            cucumberBackgroundField.setAccessible(true);
//	            CucumberBackground cucumberBackground = (CucumberBackground) cucumberBackgroundField.get(cucumberScenario);
//	            background = cucumberBackground.getSteps().get(0).getName();
//	            
//	            Field cucumberFeatureField = cucumberBackground.getClass().getSuperclass().getDeclaredField("cucumberFeature");
//	            cucumberFeatureField.setAccessible(true);
//	            CucumberFeature cucumberFeature = (CucumberFeature) cucumberFeatureField.get(cucumberBackground);
//	            feature = cucumberFeature.getGherkinFeature().getDescription();
			
			} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	public static void assertTrue(boolean condition) {
		//logger.info("Verification Step-> " + getScenarioStep());
		Assert.assertTrue(condition);
		takeScreenshot();
	}
	
	public static void assertFalse(boolean condition) {
		//logger.info("Verification Step-> " + getScenarioStep());
		Assert.assertFalse(condition);
		takeScreenshot();
	}
	
	public static void executeReportJar()
	{
		String jarFile = "C:\\Cucumber_sandwich\\original_cucumber-sandwich.jar";//ReportHandler.class.getClassLoader().getResource(SetUp.getPropertyValue("jar_sandwich_report")).getPath();
		
		System.out.println(jarFile);
		String jsonFile = "target\\cucumber-json-report\\";
		String cmd = "java -jar " + jarFile + " -f " +jsonFile+" -o " + jsonFile;
		SetUp.executeCommand(cmd);
	}
	
	public static String takeSceenshot(WebElement element) {
		
		File scrFile;
		WebDriver browser = Hooks.browser;
		
		new Actions(browser).moveToElement(element);
		
		scrFile = ((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE);
		File file = new File("target\\screenshot\\"+TDataFactory.getDateTimeStamp()+".png");
		try {		
			FileUtils.copyFile(scrFile, file);
			System.out.println("Taken screenshot");
			return file.toString();
		} catch (IOException e) {
			System.out.println("Unable to take screenshot");
			e.printStackTrace();
			return null;
		}
	}
	
	public static String takeScreenshot1() {
		File scrFile;
		WebDriver browser = Hooks.browser;
		scrFile = ((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE);
		File file = new File("target\\screenshot\\"+TDataFactory.getDateTimeStamp()+".png");
		try {		
			FileUtils.copyFile(scrFile, file);
			System.out.println("Taken screenshot");
			return file.toString();
			//FileUtils.copyFile(scrFile, new File(new ClassLoader().getClass().getClassLoader().getResource("\\target\\screenshot"+TDataFactory.getDateTimeStamp()+".png").getFile()));
		} catch (IOException e) {
			System.out.println("Unable to take screenshot");
			e.printStackTrace();
			return null;
		}
	}	
	
	public static void embedScreenshot() {
		try {
			File file = takeScreenshot();
			InputStream screenshotStream = new FileInputStream(file);
			scenario.embed(IOUtils.toByteArray(screenshotStream), "image/jpeg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static File takeScreenshot() {
		File file = new File("target\\screenshot\\"+TDataFactory.getDateTimeStamp()+".png");
		WebDriver browser = Hooks.browser;
		  Dimension size = browser.manage().window().getSize(); 
	      Point point = browser.manage().window().getPosition();
	    
	      String format = "png";
	      Rectangle captureRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	      try {
	    	BufferedImage captureImage = (new Robot()).createScreenCapture(captureRect); 
			ImageIO.write(captureImage, format, file);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (AWTException e) {
			System.out.println(e.getMessage());
		}	      
	      return file;
	}
}