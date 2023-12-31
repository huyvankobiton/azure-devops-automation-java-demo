/*
 * Created on Sep 28, 2017, 11:55:54 AM
 *
 * Copyright(c) 2017 Kobiton Inc.  All Rights Reserved.
 * This software is the proprietary information of Kobiton Company.
 *
 */

package com.pcc.testngAzureDemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeSuite;
import static org.testng.util.Strings.isNullOrEmpty;

public class BaseTest extends TestListenerAdapter {
    private String automationUrl;
    private final String username;
    private final String apiKey;
    private String hostName = "api.kobiton.com";

    public BaseTest() {
        this.username = "kobitonadmin+businesscloud";
        this.apiKey = "884f4fc8-3fbe-42d0-90f3-3fa706d6554a";

        String envHostName = "@api.kobiton.com";
        if (envHostName != null) {
            this.hostName = envHostName;
        }

        if (this.username != null && this.apiKey != null) {
            this.automationUrl = "https://" + this.username + ":"+ this.apiKey + envHostName + "/wd/hub";
        }
    }

    @BeforeSuite
    protected void Setup() throws Exception {
        if (isNullOrEmpty(username)) {
            Assert.fail("Username was missing");
        }

        if (isNullOrEmpty(apiKey)) {
            Assert.fail("APIKey was missing");
        }
    }

    @Override
    public void onTestFailure(ITestResult itr) {
        super.onTestFailure(itr);

        System.out.println("Test failed!");
        if (itr.getThrowable() != null) {
            itr.getThrowable().printStackTrace();
        }
    }

    protected URL getAutomationUrl() throws MalformedURLException {
        return new URL( automationUrl);
    }

    protected String getHostName() {
        return hostName;
    }
    
    protected String getAuthorization() {
        String keySource = String.format("%s:%s", this.username, this.apiKey);
        String base64Key = Base64.getEncoder().encodeToString(keySource.getBytes());
        
        return String.format("Basic %s", base64Key);
    }
}