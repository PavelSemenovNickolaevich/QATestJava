package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ChangePasswordTest extends  TestBase {
    @BeforeMethod
    public void startMailServer () {
        applicationManager.mail().start();
    }

    @Test
    public void changePassword() {
        String admin = "administrator";
        String passwordAdmin = "root";
    }


    @AfterMethod(alwaysRun = true)
    public void stopMailServer () {
        applicationManager.mail().stop();
    }
}
