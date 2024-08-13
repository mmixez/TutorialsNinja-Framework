package com.tutorialsninja.qa.testcases;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountSuccessPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.RegisterPage;
import com.tutorialsninja.qa.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegisterTest extends Base {

    public RegisterTest() throws IOException {
        super();
    }

    public WebDriver driver;

    @BeforeMethod
    public void setup() {

        driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
        HomePage homePage = new HomePage(driver);
        homePage.clickOnMyAccount();
        homePage.selectRegisterOption();
       //driver.findElement(By.xpath("//a[@title='My Account']")).click();
       //driver.findElement(By.linkText("Register")).click();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


    @Test (priority = 1)
    public void verifyRegisteringAnAccountWithMandatoryFields() {

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterFirstName(dataProp.getProperty("firstName"));
        registerPage.enterLastName(dataProp.getProperty("lastName"));
        registerPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
        registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
        registerPage.enterPassword(prop.getProperty("validPassword"));
        registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
        registerPage.selectPrivacyPolicy();
        registerPage.clickOnContinueButton();
        //driver.findElement(By.id("input-firstname")).sendKeys(dataProp.getProperty("firstName"));
        //driver.findElement(By.id("input-lastname")).sendKeys(dataProp.getProperty("lastName"));
        //driver.findElement(By.id("input-email")).sendKeys(Utilities.generateEmailWithTimeStamp());
        //driver.findElement(By.id("input-telephone")).sendKeys(dataProp.getProperty("telephoneNumber"));
        //driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
        //driver.findElement(By.id("input-confirm")).sendKeys(prop.getProperty("validPassword"));
        //driver.findElement(By.name("agree")).click();
        //driver.findElement(By.xpath("//input[@value=\"Continue\"]")).click();

        AccountSuccessPage accountSuccessPage = new AccountSuccessPage(driver);
        String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
       // String actualSuccessHeading = driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
        Assert.assertEquals(actualSuccessHeading,dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Account Success page is not displayed");

    }
    @Test (priority = 2)
    public void verifyRegisteringAccountByProvidingAllFields() {

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterFirstName(dataProp.getProperty("firstName"));
        registerPage.enterLastName(dataProp.getProperty("lastName"));
        registerPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());
        registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
        registerPage.enterPassword(prop.getProperty("validPassword"));
        registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
        registerPage.selectYesNewsletterOption();
        registerPage.selectPrivacyPolicy();
        registerPage.clickOnContinueButton();
        //driver.findElement(By.id("input-firstname")).sendKeys(dataProp.getProperty("firstName"));
        //driver.findElement(By.id("input-lastname")).sendKeys(dataProp.getProperty("lastName"));
        //driver.findElement(By.id("input-email")).sendKeys(Utilities.generateEmailWithTimeStamp());
        //driver.findElement(By.id("input-telephone")).sendKeys(dataProp.getProperty("telephoneNumber"));
        //driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
        //driver.findElement(By.id("input-confirm")).sendKeys(prop.getProperty("validPassword"));
        //driver.findElement(By.xpath("//input[@name=\"newsletter\"][@value=1]")).click();
        //driver.findElement(By.name("agree")).click();
       // driver.findElement(By.xpath("//input[@value=\"Continue\"]")).click();

        AccountSuccessPage accountSuccessPage = new AccountSuccessPage(driver);
        String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
        Assert.assertEquals(actualSuccessHeading,dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Account Success page is not displayed");

    }
    @Test (priority = 3)
    public void verifyRegisteringAccountWithExistingEmailAddress() {


        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterFirstName(dataProp.getProperty("firstName"));
        registerPage.enterLastName(dataProp.getProperty("lastName"));
        registerPage.enterEmailAddress(prop.getProperty("validEmail"));
        registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
        registerPage.enterPassword(prop.getProperty("validPassword"));
        registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
        registerPage.selectYesNewsletterOption();
        registerPage.selectPrivacyPolicy();
        registerPage.clickOnContinueButton();
        String actualWarning = registerPage.retrieveDuplicateEmailAddressWarning();
        Assert.assertTrue(actualWarning.contains(dataProp.getProperty("duplicateEmailWarning")),"Warning message is not displayed regarding existing email address");
        //driver.findElement(By.id("input-firstname")).sendKeys(dataProp.getProperty("firstName"));
        //driver.findElement(By.id("input-lastname")).sendKeys(dataProp.getProperty("lastName"));
        //driver.findElement(By.id("input-email")).sendKeys(prop.getProperty("validEmail"));
        //driver.findElement(By.id("input-telephone")).sendKeys(dataProp.getProperty("telephoneNumber"));
        //driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
        //driver.findElement(By.id("input-confirm")).sendKeys(prop.getProperty("validPassword"));
        //driver.findElement(By.xpath("//input[@name=\"newsletter\"][@value=1]")).click();
        //driver.findElement(By.name("agree")).click();
        //driver.findElement(By.xpath("//input[@value=\"Continue\"]")).click();
       // String actualWarning = driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).getText();


    }
    @Test (priority = 4)
    public void verifyRegisteringAccountWithoutFillingAnyDetails() {

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickOnContinueButton();

        String actualPrivacyPolicyWarning = registerPage.retrievePrivacyPolicyWarning();
        //driver.findElement(By.xpath("//input[@value=\"Continue\"]")).click();
        //String actualprivacyPolicyWarning = driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).getText();
        Assert.assertTrue(actualPrivacyPolicyWarning.contains(dataProp.getProperty("privacyPolicyWarning")),"Privacy Policy Warning message is not displayed");

        String actualFirstNameWarning = registerPage.retrieveFirstNameWarning();
       // String actualFirstNameWarning = driver.findElement(By.xpath(" ")).getText();
        Assert.assertEquals(actualFirstNameWarning,dataProp.getProperty("firstNameWarning"),"First Name Warning message is not displayed");

        String actualLastNameWarning = registerPage.retrieveLastNameWarning();
       // String actualLastNameWarning = driver.findElement(By.xpath("//input[@id=\"input-lastname\"]/following-sibling::div")).getText();
        Assert.assertEquals(actualLastNameWarning,dataProp.getProperty("lastNameWarning"),"Last Name Warning message is not displayed");

        String actualEmailWarning = registerPage.retrieveEmailWarning();
        //String actualEmailWarning = driver.findElement(By.xpath("//input[@id=\"input-email\"]/following-sibling::div")).getText();
        Assert.assertEquals(actualEmailWarning,dataProp.getProperty("emailWarning"),"Email Address Warning message is not displayed");

        String actualTelephoneWarning = registerPage.retrieveTelephoneWarning();
        //String actualTelephoneWarning = driver.findElement(By.xpath("//input[@id=\"input-telephone\"]/following-sibling::div")).getText();
        Assert.assertEquals(actualTelephoneWarning,dataProp.getProperty("telephoneWarning"),"Telephone Warning message is not displayed");

        String actualPasswordWarning = registerPage.retrievePasswordWarning();
        //String actualPasswordWarning = driver.findElement(By.xpath("//input[@id=\"input-password\"]/following-sibling::div")).getText();
        Assert.assertEquals(actualPasswordWarning,dataProp.getProperty("passwordWarning"),"Password Warning message is not displayed");
    }

}
