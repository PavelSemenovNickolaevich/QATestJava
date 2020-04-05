package ru.stqa.pft.mantis.appmanger;

import org.openqa.selenium.WebDriver;

public class RegistrationHelper {
    private final ApplicationManager applicationManager;
    private WebDriver wd;

    public RegistrationHelper (ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
        wd = applicationManager.getDriver();
    }

    public void start (String username, String email) {
        wd.get(applicationManager.getProperty("web.baseUrl") + "/signup_page.php");
    }
}
