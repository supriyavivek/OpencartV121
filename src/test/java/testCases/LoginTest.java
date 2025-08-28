package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class LoginTest extends BaseClass {
	
  @Test(groups= {"Sanity","Master"})
  public void verify_login() {
//	  try {
		  logger.info("****Starting verify_login****");
		  HomePage hp=new HomePage(driver);
		  hp.clickMyAccount();
		  hp.clickLogin();
		 
		  LoginPage lp=new LoginPage(driver);
		  lp.setEmail(properties.getProperty("email"));
		  lp.setPassword(properties.getProperty("password"));
		  lp.clickLogin();
		  
		  MyAccountPage myAccPage=new MyAccountPage(driver);
		  String myAccount = myAccPage.verifyHeader();
		  Assert.assertEquals(myAccount, "My Account");
		  
		  hp.clickMyAccount();
		  myAccPage.clickLogout();
		  myAccPage.clickContinueLink();
//	  } catch (Exception e) {
//		  logger.error("Test failed"+e);
//	  }
	  logger.info("****finished verify_login****");
  }
}
