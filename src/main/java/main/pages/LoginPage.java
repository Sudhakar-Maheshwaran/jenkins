package main.pages;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	WebDriver driver;
	
	
	//locating element
	@FindBy(id = "login-button")
	WebElement loginButton;
	
	@FindBy(id = "user-name")
	WebElement userName;
	
	@FindBy(id = "password")
	WebElement passWord;
	
	@FindBy(xpath = "//h3[@data-test='error']")
	WebElement error;
	
	
	//initialise part 
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//actions
	public void enterUserName(String userNameValue) {
		userName.sendKeys(userNameValue);
	}
	
	public void enterpassword(String passwordValue) {
		passWord.sendKeys(passwordValue);
	}
	
	public void loginButtonClick() {
		loginButton.click();
	}
	
	public String getError() {
		return error.getText();
	}
	
	public void login(String userNameValue, String passwordValue) {
		userName.sendKeys(userNameValue);
		passWord.sendKeys(passwordValue);
		loginButton.click();
	}
	
	public String getTitleofPage() {
		return driver.getTitle();
	}
}

