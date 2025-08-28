package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {
	
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//h1[normalize-space()='My Account']")
	WebElement myAccHeader;
	@FindBy(xpath="//a[normalize-space()='Logout']")
	WebElement lnkLogout;
	@FindBy(xpath="//a[normalize-space()='Continue']")
	WebElement lnkContinue;
		
	public String verifyHeader() {
		return (myAccHeader.getText());
	}
	
	public boolean isMyAccountPageExists() {
		return (myAccHeader.isDisplayed());
	}
	
	public void clickLogout() {
		lnkLogout.click();
	}
	
	public void clickContinueLink() {
		lnkContinue.click();
	}
}
