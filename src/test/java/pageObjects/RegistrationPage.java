package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage extends BasePage{
	
	public RegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//h1[normalize-space()='Register Account']")
	WebElement headerRegisterAcc;
	
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement txt_FirstName;
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txt_LastName;
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txt_Email;
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txt_Password;
	@FindBy(xpath="//button[normalize-space()='Continue']")
	WebElement btn_Continue;
	@FindBy(xpath="//input[@name='agree']")
	WebElement chk_privacyPolicy;
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement confirmationMsg;
		
	public String verifyHeader() {
		return (headerRegisterAcc.getText());
	}
	
	public void setFirstName(String fname) {
		txt_FirstName.sendKeys(fname);
	}
	
	public void setLastName(String lname) {
		txt_LastName.sendKeys(lname);
	}
	
	public void setEmail(String email) {
		txt_Email.sendKeys(email);
	}
	
	public void setPassword(String password) {
		txt_Password.sendKeys(password);
	}
	
	public void clickPrivacyPolicy() {
		try {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.elementToBeClickable(chk_privacyPolicy));
//		chk_privacyPolicy.click();
		Thread.sleep(5000);
		Actions act=new Actions(driver);
		act.moveToElement(chk_privacyPolicy).click().perform();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void clickContinue() {
		try {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
			wait.until(ExpectedConditions.elementToBeClickable(btn_Continue));
//			Thread.sleep(5000);
			Actions act=new Actions(driver);
			act.moveToElement(btn_Continue).click().perform();
			} catch (Exception e) {
				System.out.println(e);
			}
//		Actions act=new Actions(driver);
//		act.moveToElement(btn_Continue).click().perform();
//		btn_Continue.click();
	}
	
	public String getConfirmationMsg() {
		try {
		return (confirmationMsg.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}
	}
	
	
	
}
