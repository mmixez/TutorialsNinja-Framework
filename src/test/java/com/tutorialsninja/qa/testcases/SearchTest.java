package com.tutorialsninja.qa.testcases;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.SearchPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class SearchTest extends Base {

    public WebDriver driver;

    public SearchTest() throws IOException {
        super();
    }

    @BeforeMethod
    public void setup() {
        driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
    }


    @Test (priority = 1)
    public void verifySearchWithValidProduct(){

        HomePage homePage = new HomePage(driver);
        homePage.enterProductNameIntoSearchBoxField(dataProp.getProperty("validProduct"));
        //driver.findElement(By.xpath("//input[@name=\"search\"][@class=\"form-control input-lg\"]")).sendKeys(dataProp.getProperty("validProduct"));

        homePage.clickOnSearchButton();
        //driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg']")).click();

        SearchPage searchPage = new SearchPage(driver);
        Assert.assertTrue(searchPage.displayStatusOfHPValidProduct(),"Valid HP product is not displayed in the search results");

    }
    @Test (priority = 2)
    public void verifyingSearchWithInvalidProduct(){

        HomePage homePage = new HomePage(driver);
        homePage.enterProductNameIntoSearchBoxField(dataProp.getProperty("invalidProduct"));
        homePage.clickOnSearchButton();
        //driver.findElement(By.xpath("//input[@name=\"search\"][@class=\"form-control input-lg\"]")).sendKeys(dataProp.getProperty("invalidProduct"));
        //driver.findElement(By.xpath("//div[@id=\"search\"]/descendant::button")).click();

        SearchPage searchPage = new SearchPage(driver);
        String actualSearchMessage = searchPage.retrieveNoProductMessageText();
      // String actualSearchMessage = driver.findElement(By.xpath("//div[@id=\"content\"]/h2/following-sibling::p")).getText();
        Assert.assertEquals(actualSearchMessage,dataProp.getProperty("NoProductTextInSearchResults"),"No product in search results displayed");

    }

    @Test (priority = 3)
    public void verifySearchWithoutAnyProduct() {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnSearchButton();
        //driver.findElement(By.xpath("//input[@name='search']")).sendKeys("");
        //driver.findElement(By.xpath("//div[@id=\"search\"]/descendant::button")).click();
       // String actualSearchMessage = driver.findElement(By.xpath("//div[@id=\"content\"]/h2/following-sibling::p")).getText();

        SearchPage searchPage = new SearchPage(driver);
        String actualSearchMessage = searchPage.retrieveNoProductMessageText();
        Assert.assertEquals(actualSearchMessage,dataProp.getProperty("NoProductTextInSearchResults"),"No product in search results displayed");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
