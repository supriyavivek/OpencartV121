package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public static WebDriver driver;
	String repName;
	
	public static void setDriver(WebDriver driverInstance) {
		driver=driverInstance;
	}
	
	public void onStart(ITestContext testContext) {
//		SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.DD.HH.mm.ss");
//		Date dt=new Date();
//		String currentdatetimestamp=df.format(dt);
		
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());  //time stamp
		repName="Test-Report-" + timeStamp + ".html";
		sparkReporter =new ExtentSparkReporter(System.getProperty("user.dir")+ "/reports/" + repName); //specify location of the report
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report"); //title of the report
		sparkReporter.config().setReportName("opencart Functional Testing"); //Name of the report
		sparkReporter.config().setTheme(Theme.STANDARD);
		
		extent =new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os=testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}
	
	public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
    }
	
	public void onTestSuccess(ITestResult result) {
		test.assignCategory(result.getMethod().getGroups());  //to display groups in report
		test.log(Status.PASS, result.getName()+ " test case got successfully executed");
	}
	
	public void onTestFailure(ITestResult result) {
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName()+" failed");
		test.log(Status.INFO,result.getThrowable().getMessage());
		
		//Capture screenshot
		if (driver != null) {
			try {
				//timestamp for uniqueness
				String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
				File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
				
				String targetFilePath = System.getProperty("user.dir")+ "/screenshots/" + result.getName() + "_" + timeStamp + ".png";
				
				//copy file instead  			
				Files.copy(sourceFile.toPath(), new File(targetFilePath).toPath(),StandardCopyOption.REPLACE_EXISTING);
				test.addScreenCaptureFromPath(targetFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			}				
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " test case skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	 
	public void onFinish(ITestContext testContext) {
		extent.flush();
		
		String pathOfExtentReport = System.getProperty("user.dir") + "/reports/" +repName;
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		//send report as email		
//		try { 
//			 URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
//		 
////		  Create the email message
//		 	 ImageHtmlEmail email = new ImageHtmlEmail();
//			 email.setDataSourceResolver(new DataSourceUrlResolver(url));
//			 email.setHostName("smtp.googleemail.com");
//			 email.setSmtpPort(465);
//			 email.setAuthenticator(new DefaultAuthenticator("priyagovindan@gmail.com","test123"));
//			 email.setSSLOnConnect(true);
//			 email.setFrom("priyagovindan@gmail.com"); //Sender
//			 email.setSubject("Test Results");
//			 email.setMsg("Please find attached report...");
//			 email.addTo("abc123@gmail.com"); //Receiver
//			 email.attach(url, "extent report", "Please check report....");
//			 email.send(); //send the email
//		 } catch (Exception e) {
//			 e.printStackTrace();
//		 }
		
//		// Send report as email
//		    try {
//		        // Create the email message
//		        EmailAttachment attachment = new EmailAttachment();
//		        attachment.setPath(extentReport.getAbsolutePath());
//		        attachment.setDisposition(EmailAttachment.ATTACHMENT);
//		        attachment.setDescription("Test Execution Report");
//		        attachment.setName(repName);
//
//		        MultiPartEmail email = new MultiPartEmail();
//		        email.setHostName("smtp.gmail.com");
//		        email.setSmtpPort(465);
//		        email.setAuthenticator(new DefaultAuthenticator("yourEmail@gmail.com", "yourAppPassword"));
//		        email.setSSLOnConnect(true);
//		        email.setFrom("yourEmail@gmail.com", "Automation Test Reports");
//		        email.setSubject("Automation Test Execution Report");
//		        email.setMsg("Hello Team,\n\nPlease find attached the latest automation test execution report.\n\nRegards,\nQA Automation");
//		        
//		        // Recipient list
//		        email.addTo("recipient1@gmail.com");
//		        email.addTo("recipient2@gmail.com");
//
//		        // Attach the report
//		        email.attach(attachment);
//
//		        // Send email
//		        email.send();
//		        System.out.println("✅ Email Sent with Report Attached...");
//		    } catch (Exception e) {
//		        e.printStackTrace();
//		    }
	}
}
