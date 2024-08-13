package com.tutorialsninja.qa.testcases;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.LoginPage;
import com.tutorialsninja.qa.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class LoginTest extends Base {

    public LoginTest() throws IOException {
        super();
    }

    public WebDriver driver;

    @BeforeMethod
    public void setup() {

        driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
        HomePage homePage = new HomePage(driver);
        homePage.clickOnMyAccount();
        homePage.selectLoginOption();
        //driver.findElement(By.xpath("//a[@title='My Account']")).click();
       // driver.findElement(By.linkText("Login")).click();


    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @DataProvider (name="validCredentialsSupplier")
    public Object[][] supplyTestData() {
        Object[][] data = Utilities.getTestDataFromExcel("Login");
                //{ {"mmixez3@gmail.com","yk123"}, {"mmixez4@gmail.com","yk123"}, {"mmixez5@gmail.com","yk123"}};
        return data;
    }


    @Test (priority = 1,dataProvider = "validCredentialsSupplier")
    public void verifyLoginWithValidCredentials (String email,String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmailAddress(email);
        loginPage.enterPassword(password);
        loginPage.clickOnLoginButton();
       // driver.findElement(By.id("input-email")).sendKeys(email);
       // driver.findElement(By.id("input-password")).sendKeys(password);
       // driver.findElement(By.xpath("//input[@value='Login']")).click();
        AccountPage accountPage = new AccountPage(driver);
        Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(),"Edit Your Account Information option is not displayed");

    }

    @Test (priority = 2)
    public void verifyLoginWithInvalidCredentials() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
        loginPage.enterPassword(dataProp.getProperty("invalidPassword"));
        loginPage.clickOnLoginButton();
        //driver.findElement(By.id("input-email")).sendKeys("mmixez4" + Utilities.generateEmailWithTimeStamp());
       // driver.findElement(By.id("input-password")).sendKeys(dataProp.getProperty("invalidPassword"));
       // driver.findElement(By.xpath("//input[@value='Login']")).click();
        String actualWarningMsg = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
        String expectedWarningMsg = dataProp.getProperty("emailPasswordNoMatchWarning");
        Assert.assertTrue (actualWarningMsg.contains(expectedWarningMsg),"Expected Warning message is not displayed");

    }
    @Test (priority = 3)
    public void verifyLoginWithInvalidEmailAndValidPassword() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
        loginPage.enterPassword(prop.getProperty("validPassword"));
        loginPage.clickOnLoginButton();
        //driver.findElement(By.id("input-email")).sendKeys("mmixez4" + Utilities.generateEmailWithTimeStamp());
       //driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
        //driver.findElement(By.xpath("//input[@value='Login']")).click();
        String actualWarningMsg = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
        String expectedWarningMsg = dataProp.getProperty("emailPasswordNoMatchWarning");
        Assert.assertTrue (actualWarningMsg.contains(expectedWarningMsg),"Expected Warning message is not displayed");

    }

    @Test (priority = 4)
    public void verifyLoginWithValidEmailAndInvalidPassword() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmailAddress(prop.getProperty("validEmail"));
        loginPage.enterPassword(dataProp.getProperty("invalidPassword"));
        loginPage.clickOnLoginButton();
        //driver.findElement(By.id("input-email")).sendKeys(prop.getProperty("validEmail"));
       //driver.findElement(By.id("input-password")).sendKeys(dataProp.getProperty("invalidPassword"));
        //driver.findElement(By.xpath("//input[@value='Login']")).click();
        String actualWarningMsg = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
        String expectedWarningMsg = dataProp.getProperty("emailPasswordNoMatchWarning");
        Assert.assertTrue (actualWarningMsg.contains(expectedWarningMsg),"Expected Warning message is not displayed");

    }

    @Test (priority = 5)
    public void verifyLoginWithoutProvidingCredentials() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnLoginButton();

       // driver.findElement(By.id("input-email")).sendKeys("");
        //driver.findElement(By.id("input-password")).sendKeys("");
        //driver.findElement(By.xpath("//input[@value='Login']")).click();
        String actualWarningMsg = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
        String expectedWarningMsg = dataProp.getProperty("emailPasswordNoMatchWarning");
        Assert.assertTrue (actualWarningMsg.contains(expectedWarningMsg),"Expected Warning message is not displayed");

    }




}
