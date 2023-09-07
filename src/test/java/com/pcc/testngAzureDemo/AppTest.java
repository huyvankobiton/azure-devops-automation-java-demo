package com.pcc.testngAzureDemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterSuite;

import org.testng.annotations.Test;
import io.appium.java_client.MobileElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class AppTest {
  public static URL url;
  public static DesiredCapabilities capabilities;
  public static AndroidDriver<MobileElement> driver;
  //@Parameters({ deviceName_,"platformVersion_"})
  @BeforeSuite
  public void setupAppium() throws MalformedURLException {
    //Run test on Kobiton
	  
	  
    //The generated session will be visible to you only. 
    String kobitonServerUrl = "https://" + "kobitonadmin+businesscloud" + ":" + "884f4fc8-3fbe-42d0-90f3-3fa706d6554a" + "@api.kobiton.com/wd/hub";
    DesiredCapabilities capabilities = new DesiredCapabilities();
    // The generated session will be visible to you only. 
    capabilities.setCapability("sessionName", "PMO test executor");
    capabilities.setCapability("sessionDescription", "automation");
    capabilities.setCapability("deviceOrientation", "portrait");
    capabilities.setCapability("noReset", true);
    capabilities.setCapability("fullReset", false);
    capabilities.setCapability("captureScreenshots", true);
    capabilities.setCapability("useConfiguration", "");
    capabilities.setCapability("autoWebview", true);
    capabilities.setCapability("browserName", "chrome");
    // The maximum size of application is 500MB
    // By default, HTTP requests from testing library are expired
    // in 2 minutes while the app copying and installation may
    // take up-to 30 minutes. Therefore, you need to extend the HTTP
    // request timeout duration in your testing library so that
    // it doesn't interrupt while the device is being initialized.
    // String app = System.getenv("KOBITON_SESSION_APPLICATION") != null ? System.getenv("KOBITON_SESSION_APPLICATION") : "kobiton-store:91041";
    // capabilities.setCapability("app", app);

    capabilities.setCapability("groupId", 3159); // Group: ROYAL
    capabilities.setCapability("deviceGroup", "ORGANIZATION");
    // For deviceName, platformVersion Kobiton supports wildcard
    // character *, with 3 formats: *text, text* and *text*
    // If there is no *, Kobiton will match the exact text provided
    
    // String deviceName = System.getenv("KOBITON_DEVICE_NAME") != null ? System.getenv("KOBITON_DEVICE_NAME") : "Galaxy*";
    // String platformVersion = System.getenv("KOBITON_SESSION_PLATFORM_VERSION") != null ? System.getenv("KOBITON_SESSION_PLATFORM_VERSION") : "6*";
    // String platformName = System.getenv("KOBITON_DEVICE_PLATFORM_NAME") != null ? System.getenv("KOBITON_DEVICE_PLATFORM_NAME") : "android";
    
    // capabilities.setCapability("deviceName", deviceName);
    // capabilities.setCapability("platformVersion", platformVersion);
    // capabilities.setCapability("platformName", platformName);
    capabilities.setCapability("udid", "R3CN20NFKMM");
    // capabilities.setCapability("deviceName", "Galaxy Note20 5G");
    // capabilities.setCapability("tagName", "");
    // capabilities.setCapability("platformVersion", "11");
    // capabilities.setCapability("platformName", "Android");
    // capabilities.setCapability("kobi:retainDurationInSeconds", 0);
	  
    System.out.println(capabilities);

    url = new URL(kobitonServerUrl);

    driver = new AndroidDriver<MobileElement>(url, capabilities);
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
  }

  @AfterSuite
  public void uninstallApp() throws InterruptedException {
    System.out.println("After Suite");
    String kobitonSessionId = driver.getSessionDetails().get("kobitonSessionId").toString();
    String kobitonDeviceName = driver.getSessionDetails().get("deviceName").toString();
    System.out.println("getPageSource: " + driver.getPageSource());
    System.out.println("getTitle: " + driver.getTitle());
    System.out.println("getCapabilities: " + driver.getCapabilities());
		System.out.println("getSessionDetails: " + driver.getSessionDetails());
		System.out.println("getSessionId: " + driver.getSessionId());
		System.out.println("getAutomationName: " + driver.getAutomationName());
		System.out.println("getCommandExecutor: " + driver.getCommandExecutor());
		System.out.println("getErrorHandler: " + driver.getErrorHandler());
		System.out.println("getPlatformName: " + driver.getPlatformName());
		System.out.println("getRemoteAddress: " + driver.getRemoteAddress());
		System.out.println("getExecuteMethod: " + driver.getExecuteMethod());
    // System.out.println("kobitonSessionId: " + kobitonSessionId);
    // System.out.println("getCommandExecutor: " + driver.getCommandExecutor());
    System.out.println("# Test Execution Log - Bitrise");

    long yourmilliseconds = System.currentTimeMillis();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
    Date resultdate = new Date(yourmilliseconds);
    System.out.println("**Date:** " + sdf.format(resultdate));

    System.out.println("### Test Device Used");
    System.out.println("  - " + kobitonDeviceName + " (" + driver.getPlatformName() + " " + driver.getSessionDetails().get("platformVersion").toString() + ") " + "- UIID: " + driver.getSessionDetails().get("udid").toString());
    System.out.println("  - Session URL: " + driver.getRemoteAddress() + "/" + kobitonSessionId);
    if (driver != null) {
      // System.out.println("Quitting the driver.");
      driver.quit();
    }
  }

  @Test (enabled=true) public void myFirstTest() throws InterruptedException {
    System.out.println("First Test");
    driver.get("http://google.com/");
    System.out.println("1");
    Thread.sleep(5000);
	  driver.findElementByName("q").sendKeys("Kobiton");
    System.out.println("2");
	  Thread.sleep(10000);
  }
}
