package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/* Data is valid - login success - test pass - logout
 * Data is valid - login failed - test fail 
 * 
 * Data is invalid - login success - test fail - logout
 * Data is invalid - login failed - test pass
 */

public class LoginDataDrivenTest  extends BaseClass {
	
	@Test (dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="DataDriven") //getting data provider from different class
	public void verify_loginDDT(String email, String password, String expected) {	
//	try {
		  logger.info("****Starting verify_login****");
		  //Home Page
		  HomePage hp=new HomePage(driver);
		  hp.clickMyAccount();
		  hp.clickLogin();
		 
		  //Login page
		  LoginPage lp=new LoginPage(driver);
		  lp.setEmail(email);
		  lp.setPassword(password);
		  lp.clickLogin();
		  
		  //My account page
		  MyAccountPage myAccPage=new MyAccountPage(driver);
		  boolean targetPage=myAccPage.isMyAccountPageExists(); 
		  
		  /* Data is valid - login success - test pass - logout
		   * Data is valid - login failed - test fail 
		   * 
		   * Data is invalid - login success - test fail - logout
		   * Data is invalid - login failed - test pass
		   */
		  
		  if(expected.equalsIgnoreCase("valid") ) {
			  if(targetPage==true) {
				  hp.clickMyAccount();
				  myAccPage.clickLogout();
				  Assert.assertTrue(true);
			  } else {
				  Assert.assertTrue(false);
			  }
		  } else if(expected.equalsIgnoreCase("Invalid")) {
			  if(targetPage==true) {
				  hp.clickMyAccount();
				  myAccPage.clickLogout();
				  Assert.assertTrue(false);
			  } else {
				  Assert.assertTrue(true);
			  }
		  }
		 
//		  myAccPage.clickContinueLink();
//	  } catch (Exception e) {
//		  e.printStackTrace();
//	  }
	  logger.info("****finished verify_login****");
	}
}
 