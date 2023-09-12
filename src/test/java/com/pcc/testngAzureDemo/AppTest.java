package com.pcc.testngAzureDemo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Date;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import org.testng.Assert;
import io.appium.java_client.MobileElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class AppTest extends BaseTest {
  public static AndroidDriver<MobileElement> driver;
  @BeforeSuite
  public void setupAppium() throws Exception {
    super.Setup();

    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("sessionName", "PMO test executor");
    capabilities.setCapability("sessionDescription", "automation");
    capabilities.setCapability("deviceOrientation", "portrait");
    capabilities.setCapability("noReset", true);
    capabilities.setCapability("fullReset", false);
    capabilities.setCapability("captureScreenshots", true);
    capabilities.setCapability("useConfiguration", "");
    capabilities.setCapability("autoWebview", true);
    capabilities.setCapability("browserName", "chrome");
    capabilities.setCapability("groupId", 512);
    capabilities.setCapability("deviceGroup", "ORGANIZATION");
    capabilities.setCapability("udid", "R58M20D1ELE"); // Custom input device
//    String deviceName = System.getenv("KOBITON_DEVICE_NAME") != null ? System.getenv("KOBITON_DEVICE_NAME") : "Galaxy*";
//    String platformVersion = System.getenv("KOBITON_SESSION_PLATFORM_VERSION") != null ? System.getenv("KOBITON_SESSION_PLATFORM_VERSION") : "6*";
//    String platformName = System.getenv("KOBITON_DEVICE_PLATFORM_NAME") != null ? System.getenv("KOBITON_DEVICE_PLATFORM_NAME") : "android";
//    capabilities.setCapability("deviceName", deviceName);
//    capabilities.setCapability("platformVersion", platformVersion);
//    capabilities.setCapability("platformName", platformName);
    System.out.println("capabilities" + capabilities);
    System.out.println("getAutomationUrl" + getAutomationUrl());

    driver = new AndroidDriver<MobileElement>(getAutomationUrl(), capabilities);
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
  }

  @AfterSuite
  public void uninstallApp() throws InterruptedException, IOException {
    System.out.println("After Suite");
    String kobitonSessionId = driver.getSessionDetails().get("kobitonSessionId").toString();
    String kobitonDeviceName = driver.getSessionDetails().get("deviceName").toString();
    // System.out.println("getPageSource: " + driver.getPageSource());
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

    long timeMillis = System.currentTimeMillis();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
    Date resultdate = new Date(timeMillis);
    System.out.println("**Date:** " + sdf.format(resultdate));

    String url = String.format("https://%s/v1/sessions/%s", getHostName(), kobitonSessionId);
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url(url)
            .header("Authorization", getAuthorization())
            .build();
    Response response = client.newCall(request).execute();
    Assert.assertNotNull(response.body(), "Response body is null");
    String responseBodyString = response.body().string();
    System.out.println("response body: " + responseBodyString);
    Assert.assertNotNull(responseBodyString, "Response body string is null");

    System.out.println("# Test Execution Log - Bitrise");
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
