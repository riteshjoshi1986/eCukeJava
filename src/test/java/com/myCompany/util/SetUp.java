package com.myCompany.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class SetUp {
	private static	WebDriver driver;
	private static String propfolder = "com/myCompany/myProject/data/";
	private static String propFile_data = propfolder + "data.properties";
	
	private static Map<String,String> map = new HashMap<String,String>();
	private static String sauce_username;
	private static String sauce_key;
	private static String sauceConnectTunnel;
	
	private static String remoteURL;
	private static DesiredCapabilities dc = new DesiredCapabilities();
	private static Properties systemProperties;
	
	private SetUp() {}
	
	public static WebDriver init(String browserName, String browserVersion){
		try {
			switch (browserName.toLowerCase()) {
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "ie":
				driver = new InternetExplorerDriver();
				break;
			case "chrome":
				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
				driver = new ChromeDriver();
				break;
			case "grid":
				dc = dc.chrome();
				dc.setCapability("version", "");
				dc.setCapability("platform", "LINUX");
				
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
				break;
				
			case "saucelabs":

				properties(propFile_data);
				
				sauce_username = getPropertyValue("sauce_username");
				sauce_key = getPropertyValue("sauce_key");
				sauceConnectTunnel = getPropertyValue("sauceConnectTunnel");
				remoteURL = "http://" + sauce_username + ":" + sauce_key + "@ondemand.saucelabs.com:80/wd/hub";
				ReportHandler.consolePrint("--------Sauce Labs--------");
				ReportHandler.consolePrint(remoteURL);

				URL sauceURL = new URL(remoteURL);			
				dc.setCapability("parent-tunnel", sauceConnectTunnel);
				dc.setCapability("name", "Test - Chrome");
				dc.setCapability("max-duration", 2700);
				dc.setCapability("command-timeout", 600);

				systemProperties = System.getProperties();
				systemProperties.setProperty("http.proxyHost",
						"proxy");
				systemProperties.setProperty("http.proxyPort", "8099");
				
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				options.addArguments("--disable-extensions");
				dc.setBrowserName("chrome");
				dc.setVersion("53.0");
				dc.setCapability("platform", "WINDOWS 7");
				dc.setCapability(ChromeOptions.CAPABILITY, options);
				
				driver = new RemoteWebDriver(sauceURL, dc);

				break;			
		}
		
		driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
		}catch(Exception e){
			System.out.print(e.getMessage());
			return null;
		}		
	}
	
	public static void quit() {
		// driver.quit();
		driver.close();
	}
	
	public static Map<String,String> properties(String file){
		try{
			Properties prop = new Properties();
			prop.load(SetUp.class.getClassLoader().getResourceAsStream(file));
			map.clear();			
			for (final String key: prop.stringPropertyNames())
				map.put(key,prop.getProperty(key));
			return map;
			}catch(Exception e){
				new ExceptionHandler(e);
				return null;
			}
	}
	
	public static Map<String,String> dataInJson(String file) {
		Map<String,String> temp=new HashMap<String,String>();
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir")+"/src/test/resources/" + file));
			for(Object obj_i: obj.keySet()){
				temp.put((String) obj_i,(String) obj.get(obj_i.toString()));
			}
//			for(int i=0;i<obj.keySet().size();i++){
//				temp.put((String) obj.keySet().toArray()[i],(String) obj.get(obj.keySet().toArray()[i]));
//			}
			return temp;
		} catch (Exception e) {
			ReportHandler.consolePrint("Unable to create Json --> Map");
			new ExceptionHandler(e);
			return null;
		}	
	}
	
	public static String getPropertyValue(String key) {
		return map.get(key);
	}
	
	public static void executeCommand(String command) {
		try {
			Runtime.getRuntime().exec(command);
			WaitHandler.staticSmallWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Get the Resource File path
	public static String getResourceFilePath(String fileName) {
		return SetUp.class.getClassLoader().getResource(fileName).toString().substring(6);
	}
}