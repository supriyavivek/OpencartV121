package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.MyAccountPage;
import pageObjects.RegistrationPage;
import testBase.BaseClass;

public class AccountRegistrationTest extends BaseClass {

	@Test(groups= {"Regression","Master"})
	public void verify_Account_Registration() {
//		try {
			logger.info("****Starting AccountRegistrationTest****");
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on account link.... ");
			
			hp.clickRegister();
			logger.info("Clicked on registration link.... ");
			
			RegistrationPage reg=new RegistrationPage(driver);
			logger.info("Providing customer details.... ");
			reg.setFirstName(randomAlphabetic(5).toUpperCase());
			reg.setLastName(randomAlphabetic(5).toUpperCase());
			reg.setEmail(randomAlphabetic(5).toLowerCase()+"@gmail.com");
			reg.setPassword(randomAlphabetic(3)+"@"+randomNumeric(3));
			reg.clickPrivacyPolicy();
			reg.clickContinue();
			
			logger.info("Validating expected message.... ");
			String confMsg=reg.getConfirmationMsg();
			Assert.assertEquals(confMsg, "Your Account Has Been Created!");
			
			MyAccountPage myAccPage=new MyAccountPage(driver);
			myAccPage.clickContinueLink();
			
			logger.info("Logout after registering....");
			hp.clickMyAccount();
			myAccPage.clickLogout();
			myAccPage.clickContinueLink();
//		} catch (Exception e) {
//			logger.error("Test failed...", e);
//			System.out.println(e);
//		}
		
		logger.info("****Finished AccountRegistrationTest****");
	}
}
