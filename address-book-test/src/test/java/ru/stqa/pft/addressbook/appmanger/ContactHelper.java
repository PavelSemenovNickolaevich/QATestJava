package ru.stqa.pft.addressbook.appmanger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper (WebDriver wd) {
        super(wd);
    }

    public void logoutContact () {
        clickContact(By.linkText("Logout"));
    }

    public void fillContactInfo (ContactData contactData) {
        typeContact(By.name("firstname"), contactData.getFirstname());
        typeContact(By.name("lastname"), contactData.getLastName());
        typeContact(By.name("middlename"), contactData.getMiddlename());
        //   typeContact(By.name("company"), contactData.getCompany());
        // wd.findElement(By.name("company")).click();
        typeContact(By.name("company"), contactData.getCompany());
        typeContact(By.name("address"), contactData.getAdress());
        typeContact(By.name("home"), contactData.getPhoneHome());
        typeContact(By.name("mobile"), contactData.getPhoneMobile());
        typeContact(By.name("work"), contactData.getPhoneWork());
        typeContact(By.name("email"), contactData.getEmailOne());
        typeContact(By.name("email2"), contactData.getEmailTwo());

    }

    public void returnHomeContact () {
        clickContact(By.linkText("home page"));
    }

    public void submitContact () {
        clickContact(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void editContact (int index) {
        clickContact(By.linkText("home"));
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
        //clickContact(By.xpath("//img[@alt='Edit']"));
    }

    public void deleteContact (int index) {
        selectContact(index);
        deleteContact();
        home();
    }

    public void deleteContact (ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        home();

    }

    public void modifyContact (ContactData contact, int index) {
        editContact(index);
        fillContactInfo(contact);
        updateContact();
        returnHomeContact();
    }

    public void modifyContactNew (ContactData contact) {
        //  editContact(index);
        clickModifyContactById(contact.getId());
        fillContactInfo(contact);
        updateContact();
        returnHomeContact();
    }

    public void createNew (ContactData contact) {
        fillContactInfo(contact);
        submitContact();
        returnHomeContact();
    }


    public void updateContact () {
        clickContact(By.name("update"));
        //   clickContact(By.xpath("(//input[@name='update'])[2]"));
    }

    public void deleteContact () {
        clickContact(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    public void home () {
        clickContact(By.linkText("home"));
    }

    public void createContact (ContactData contact) {
        goToContact();
        fillContactInfo(contact);
        submitContact();
        returnHomeContact();
    }

    private void goToContact () {
        clickContact(By.linkText("add new"));
    }

    public void selectContact (int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById (int id) {
        wd.findElement(By.cssSelector("input[value= '" + id + "']")).click();
    }

    public void clickModifyContactById (int id) {
        wd.findElement((By.cssSelector("a[href*='edit.php?id=" + id + "']"))).click();

    }


    public boolean isThereAContact () {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount () {
        return wd.findElements(By.name("selected[]")).size();
    }

    public Contacts getContactList () {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contacts.add(new ContactData(id, firstname, lastname, null, null, null
                    , null, null, null, null, null));
        }
        return contacts;
    }

    public Contacts all () {
        Contacts contacts = new Contacts();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String adress = cells.get(3).getText();
            //  String[] email  = cells.get(4).getText().split("\n");
            //разбить строку на фрагменты
            //  String[] phones = cells.get(5).getText().split("\n");
            String allPhones = cells.get(5).getText();
            String allEmails = cells.get(4).getText();
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            contacts.add(new ContactData(id, firstname, lastname, null, null, adress, allPhones, allEmails));
        }
        return contacts;
    }

    public ContactData infoFromEditForm (ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String homePhone = wd.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");
        String emailOne = wd.findElement(By.name("email")).getAttribute("value");
        String emailTwo = wd.findElement(By.name("email2")).getAttribute("value");
        String adress = wd.findElement(By.name("address")).getAttribute("value");
        wd.navigate().back();
        return new ContactData(contact.getId(), firstname, lastname, null, null, adress, homePhone, mobilePhone, workPhone, emailOne, emailTwo);
    }

    private void initContactModificationById (int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value = '%s'", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement((By.tagName("a"))).click();

    }

    // примеры коротких методов нахождения элемента по id
    //   wd.findElement(By.XPath(String.format("//input[@value = '%s']/../../td[8]/a", id))).click();
    //   wd.findElement(By.XPath(String.format("//tr[.//input[@value = '%s']]/td[8]/a", id))).click();
    //   wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']', id))).click();

  /*  public List<ContactData> getContactList () {
        List<ContactData> contacts  = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element: elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData(id, firstname, lastname,null,null,null, null);
            contacts.add(contact);
        }
        return contacts;
    }*/
    /*  public Set<ContactData> all () {
        Set<ContactData> contacts  = new HashSet<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element: elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData(id, firstname, lastname,null,null,null, null);
            contacts.add(contact);
        }
        return contacts;
    }*/

    public void addToGroup (int id) {
        wd.findElement(By.name("to_group")).click();
        new Select(wd.findElement(By.name("to_group")));
        wd.findElement(By.cssSelector("select[name=\"to_group\"] > option[value='" + id + "']")).click();
        click(By.name("add"));
    }

    public void removeContactFromGroup () {
        click(By.name("remove"));
    }

    public void selectGroupToDelete (int id) {
        wd.findElement(By.name("group")).click();
        new Select(wd.findElement(By.name("group")));
        wd.findElement(By.cssSelector("option[value='" + id + "']")).click();
    }
}

