package ru.stqa.pft.addressbook.appmanger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void groupPage () {
        click(By.linkText("groups"));
    }

    public void goToAddNewContact() {
        clickContact(By.linkText("add new"));
    }
    public void goToHome() {
        clickContact(By.linkText("home"));
    }
}
