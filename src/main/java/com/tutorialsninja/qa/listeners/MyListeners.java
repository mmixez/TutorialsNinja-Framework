package com.tutorialsninja.qa.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tutorialsninja.qa.utils.ExtentReporter;
import com.tutorialsninja.qa.utils.Utilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyListeners implements ITestListener {

    ExtentReports extentReport;
    ExtentTest extentTest;
    String testName;

    @Override
    public void onStart(ITestContext context) {

         extentReport = ExtentReporter.generateExtentReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        testName = result.getName();
        extentTest = extentReport.createTest(testName);
        extentTest.log(Status.INFO, testName + " started executing");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testName = result.getName();
        extentTest.log(Status.PASS, " got successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testName = result.getName();

        System.out.println("Screenshot taken");
        WebDriver driver = null;
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
//        File srcScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//        String destinationScreenshotPath = System.getProperty("user.dir") + "/Screenshots/"+testName+".png";
//        try {
//            FileHandler.copy(srcScreenshot,new File(destinationScreenshotPath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String destinationScreenshotPath = Utilities.captureScreenshot(driver,result.getName());
        extentTest.addScreenCaptureFromPath(destinationScreenshotPath);
        extentTest.log(Status.INFO,result.getThrowable());
        extentTest.log(Status.FAIL,testName+ " got failed");


    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testName = result.getName();
        extentTest.log(Status.INFO, result.getThrowable());
        extentTest.log(Status.SKIP, testName+ " got skipped");

    }

    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();

        String pathOfExtentReport = System.getProperty("user.dir") + "/test-output/ExtentReports/extentReport.html";

        File extentReport = new File(pathOfExtentReport);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
