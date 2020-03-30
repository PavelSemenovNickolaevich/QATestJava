package ru.stqa.pft.mantis.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanger.ApplicationManager;

public class TestBase {



    protected static final ApplicationManager applicationManager
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite()
    public void setUp () throws Exception {
        applicationManager.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown () throws Exception {
        applicationManager.stop();
    }

}
