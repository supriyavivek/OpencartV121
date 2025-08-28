package testBase;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager; //Log4j2
import org.apache.logging.log4j.Logger; //Log4j2
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import utilities.ExtentReportManager;

public class BaseClass {

	public static WebDriver driver;
	public static Logger logger; //Log4j2
	public static Properties properties;
	
	@BeforeClass(groups= {"Sanity", "Regression", "Master"}) //, "DataDriven"
	@Parameters({"os", "browser"})
	public void setup(String os, String browser) throws MalformedURLException {
		
		ChromeOptions options = new ChromeOptions();

        // Disable Chrome password manager + leak detection popup
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        // Optional: to avoid "Chrome is being controlled by automated test software"
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        
		logger=LogManager.getLogger(this.getClass()); //Log4j2
		
		//loading config.properties file
		try {
			FileReader file=new FileReader("./src/test/resources/config.properties");
			properties=new Properties();
			properties.load(file);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//if running in grid with a remote server set os and browser capabilities
		if (properties.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else if(os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			} else {
				System.out.println("No matching os");
				return;
			}
			
			switch (browser.toLowerCase()) {
			case "chrome" : capabilities.setBrowserName("chrome"); break;
			case "edge" : capabilities.setBrowserName("MicrosoftEdge"); break;
			case "firefox" : capabilities.setBrowserName("firefox"); break;
			default: System.out.println("Invalid browser name"); return;
			}
			
			driver=new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(), capabilities);
		} else if(properties.getProperty("execution_env").equalsIgnoreCase("local")) {
		
			//if running in local set browser
			switch (browser.toLowerCase()) {
			case "chrome" : driver=new ChromeDriver(options); break;
			case "edge" : driver=new EdgeDriver(); break;
			case "firefox" : driver=new FirefoxDriver(); break;
			default: System.out.println("Invalid browser name"); return;
			}
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(properties.getProperty("url"));
		//pass driver reference to ExtentReportManager
		ExtentReportManager.setDriver(driver);
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Sanity", "Regression", "Master"})
	public void tearDown() {
		driver.quit();
	}
	
	//method to generate random alphabetic characters
	public static String randomAlphabetic(int length) {
		String AtoZ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom random=new SecureRandom();
		
		StringBuilder sb=new StringBuilder(length);
		for(int i=0; i<length;i++) {
			sb.append(AtoZ.charAt(random.nextInt(AtoZ.length())));
		}
		return sb.toString();
	}
	
	//method to generate random numbers
	public static String randomNumeric(int length) {
		String number="0123456789";
		SecureRandom random=new SecureRandom();
		
		StringBuilder sb=new StringBuilder(length);
		for(int i=0; i<length;i++) {
			sb.append(number.charAt(random.nextInt(number.length())));
		}
		return sb.toString();
	}
}