package com.globalhome.web;

import com.globalhome.web.utilities.DriverProvider;
import com.globalhome.web.utilities.PropertiesProvider;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.platform.suite.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;

import static io.cucumber.core.options.Constants.PLUGIN_PUBLISH_ENABLED_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/globalhome/web")
@ConfigurationParameters({
        @ConfigurationParameter(
                key =        PLUGIN_PUBLISH_ENABLED_PROPERTY_NAME,
                value="false"
        ),
        @ConfigurationParameter(
                key = PLUGIN_PUBLISH_QUIET_PROPERTY_NAME,
                value="true"
        ),
        @ConfigurationParameter(
                key = PLUGIN_PROPERTY_NAME,
                value ="pretty, html:cucumber-reports/cucumber-reports.html"
        )
})
public class RunCucumberTest {
    private DriverProvider driverProvider;
    private PropertiesProvider propertiesProvider;

    @Before
    public void before(Scenario scenario) throws IOException {
        propertiesProvider = new PropertiesProvider();
        propertiesProvider.loadProperties();

        driverProvider = new DriverProvider();
        driverProvider.initDriver();
    }


    @AfterStep
    public void takeScreeshot(Scenario scenario){
        final byte[] screenshot = ((TakesScreenshot) driverProvider.getDriver())
                .getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png",  scenario.getName()); //stick it in the report
        //driverProvider.getDriver().close();
    }


    @After
    public void after(Scenario scenario) {
        driverProvider.getDriver().quit();
    }
}

