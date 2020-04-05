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

import java.io.File;

public class TestBase {



    protected static final ApplicationManager applicationManager
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite()
    public void setUp () throws Exception {
        applicationManager.init();
        applicationManager.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown () throws Exception {
        applicationManager.ftp().restore("config_inc.php.bak", "config_inc.php");
        applicationManager.stop();
    }

}
