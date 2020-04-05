package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationTest extends TestBase {

    @BeforeMethod
    public  void  startMailServer() {
        applicationManager.mail().start();
    }

    @Test
    public void testRegistration() {
        applicationManager.registration().start("user1", "123@gmail.com");
    }

    @AfterMethod(alwaysRun = true)
    public  void  stopMailServer() {
        applicationManager.mail().stop();
    }
}
