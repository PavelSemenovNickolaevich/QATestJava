package ru.stqa.pft.mantis.appmanger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.xml.transform.sax.SAXResult;

import static ru.stqa.pft.mantis.tests.TestBase.applicationManager;

public class RegistrationHelper extends HelperBase {
  //  private final ApplicationManager applicationManager;
 //   private WebDriver wd;

    public RegistrationHelper (ApplicationManager applicationManager) {
        super(applicationManager);
    //    this.applicationManager = applicationManager;
   //     wd = applicationManager.getDriver();
    }

    public void start (String username, String email) {
        wd.get(applicationManager.getProperty("web.baseUrl") + "/signup_page.php");
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[type='submit']"));
    }

    public void finish (String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("button"));
    }

    public void manageUser (String user) {
        wd.get(applicationManager.getProperty("web.baseUrl") + "/manage_use_page.php");


    }

    public void resetPassword () {
       click(By.cssSelector("input[value = 'Reset Password']"));
    }

}
