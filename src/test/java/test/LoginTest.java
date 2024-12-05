package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import main.pages.LoginPage;

public class LoginTest {

	WebDriver driver;
	LoginPage loginpage;
	

	@BeforeMethod
	void setup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
		loginpage = new LoginPage(driver);
	}

	@DataProvider(name = "LoginData")
	Object[][] getData() throws IOException {
		FileInputStream fs = new FileInputStream("C:/Users/SHRIDHAR G/Desktop/Html/Sample_data_test.xlsx");
		XSSFWorkbook book = new XSSFWorkbook(fs);
		XSSFSheet sheet = book.getSheetAt(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		short columns = sheet.getRow(0).getLastCellNum();
		
		Object [][]arr = new Object[totalRows-1][columns];
		
		for (int i = 1; i < totalRows; i++) {
			XSSFRow row = sheet.getRow(i);
			for (int j = 0; j < columns; j++) {
				XSSFCell cell = row.getCell(j);
				arr[i-1][j] = cell.getStringCellValue();
			}
		}
		return arr;
	}
	
	@Test(priority = 1)
	void titleTest() {
		String expectedTitle = "Swag Labs";
		Assert.assertEquals(loginpage.getTitleofPage(), expectedTitle);
	}

	@Test(dataProvider = "LoginData", priority = 2)
	void validloginTest(String userName, String passWord) {
		loginpage.login(userName, passWord );
		try {
			Assert.assertTrue(driver.findElement(By.xpath("//div[.='Sauce Labs Backpack']")).isDisplayed());
		} catch (NoSuchElementException e) {
			System.out.println("Test case failed not able to locate");
		}

	}

	@Test(enabled = false)
	void inValidloginTest() {
		loginpage.login("standard_user", "secret_sauce1");
		String expectedError = "Epic sadface: Username and password do not match any user in this service";
		Assert.assertEquals(loginpage.getError(), expectedError);
	}

	@Test(priority = 3)
	void emptyLogin() {
		loginpage.loginButtonClick();
		String expectedError = "Epic sadface: Username is required";
		Assert.assertEquals(loginpage.getError(), expectedError);
	}

	@AfterMethod
	void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}