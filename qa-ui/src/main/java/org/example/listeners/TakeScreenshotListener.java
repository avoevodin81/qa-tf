package org.example.listeners;

import io.qameta.allure.Allure;
import org.example.constants.TestConstants;
import org.example.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class TakeScreenshotListener extends TestListenerAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(TakeScreenshotListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        Object webDriverAttribute = result.getTestContext()
                .getAttribute(TestConstants.WEB_DRIVER);
        File screenshot = ScreenshotUtils.getScreenshot((WebDriver) webDriverAttribute);
        try {
            Allure.getLifecycle().addAttachment(UUID.randomUUID().toString(), null, null,
                    Files.newInputStream(screenshot.toPath()));
        } catch (IOException e) {
            LOGGER.warn("Could not take screenshot");
        }
    }
}
