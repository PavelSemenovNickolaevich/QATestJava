package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DeleteContactFromGroupTest extends TestBase {
    private Properties properties;

    @BeforeMethod
    public void preconditions () throws IOException {
        Groups groups = applicationManager.db().groups();
        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties"))));
        if (applicationManager.db().contacts().size() == 0) {
            applicationManager.goTo().goToHome();
            applicationManager.contact().createContact(new ContactData(properties.getProperty("web.firstname")
                    , properties.getProperty("web.lastname"), properties.getProperty("web.middlename")
                    , properties.getProperty("web.company"), properties.getProperty("web.address")
                    , properties.getProperty("web.phoneHome"), properties.getProperty("web.phoneMobile")
                    , properties.getProperty("web.phoneWork"), properties.getProperty("web.emailOne")
                    , properties.getProperty("web.emailTwo")));
        }

        if (applicationManager.db().groups().size() == 0) {
            applicationManager.goTo().groupPage();
            applicationManager.group()
                    .create(new GroupData()
                            .withName(properties.getProperty("web.nameGroup"))
                            .withHeader(properties.getProperty("web.header"))
                            .withFooter(properties.getProperty("web.footer")));
        }
    }

    @Test
    public void deleteContactFromGroupTest () {
        Contacts before = applicationManager.db().contacts();
     //   Groups groups = applicationManager.db().groups();
        ContactData contactsSelect = before.iterator().next();
        ContactData contactNow = before.iterator().next().setId(contactsSelect.getId());
        Groups groupWithContact = contactNow.setId(contactsSelect.getId()).getGroups();

        applicationManager.contact().selectGroupToDelete(groupWithContact.iterator().next().getId());
        applicationManager.contact().selectContactById(contactNow.getId());
        applicationManager.contact().removeContactFromGroup();
        applicationManager.goTo().goToHome();
        Contacts after = applicationManager.db().contacts();
        ContactData nowContact = after.iterator().next().setId(contactsSelect.getId());
        Groups groupsWithContactNow = nowContact.setId(contactsSelect.getId()).getGroups();
        Groups removeGroups = applicationManager.db().groups();
        removeGroups.removeAll(groupsWithContactNow);
        Assert.assertEquals(groupsWithContactNow, groupWithContact.without(removeGroups.iterator().next()));






    }

}
