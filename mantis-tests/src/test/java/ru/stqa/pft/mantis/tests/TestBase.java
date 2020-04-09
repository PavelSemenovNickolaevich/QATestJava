package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanger.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestBase {



    public static final ApplicationManager applicationManager
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

     boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = applicationManager.soap().getMantisConnect();
        IssueData issueData = mc.mc_issue_get("administrator", "root" , BigInteger.valueOf(issueId));
        if (issueData.getResolution().getName().equals("Fixed")) {
            return false;
        } else {
            return true;
        }

    }

    public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

}
