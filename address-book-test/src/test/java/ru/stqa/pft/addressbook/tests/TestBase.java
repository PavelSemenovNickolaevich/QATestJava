package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.appmanger.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.stream.Collectors;

@Listeners(MyTestListener.class)
public class TestBase {



    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected static final ApplicationManager applicationManager
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite()
    public void setUp (ITestContext context) throws Exception {
        applicationManager.init();
        context.setAttribute("app",applicationManager);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown () throws Exception {
        applicationManager.stop();
    }

    @BeforeMethod
    public void logTestStart (Method m, Object[] p) {
        logger.info("Start test" + m.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop (Method m) {
        logger.info("Stop test" + m.getName());
    }

    public void verifyGroupListInUI () {
        if (Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = applicationManager.db().groups();
            Groups uiGroups = applicationManager.group().all();
            MatcherAssert.assertThat(uiGroups, CoreMatchers.equalTo(dbGroups.stream().map((g) -> new GroupData()
                    .withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet())));
        }
    }

     public void verifyContactListUI () {
         if (Boolean.getBoolean("verifyUI")) {
             Contacts dbContacts = applicationManager.db().contacts();
             Groups uiGroups = applicationManager.group().all();
             MatcherAssert.assertThat(uiGroups, CoreMatchers.equalTo(dbContacts.stream().map((g) -> new ContactData(g.getId()
                     ,g.getFirstname(), g.getLastName(), g.getMiddlename(), g.getCompany(), g.getAdress(),g.getPhoneHome()
                     ,g.getPhoneMobile(),g.getPhoneWork(), g.getEmailOne(),g.getEmailOne()))
                     .collect(Collectors.toSet())));
         }
    }

}
