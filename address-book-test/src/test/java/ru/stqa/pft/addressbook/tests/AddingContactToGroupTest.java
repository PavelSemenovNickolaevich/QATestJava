package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AddingContactToGroupTest extends TestBase {
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
    public void testContactToGroupAdding () {
        Groups allGroups = applicationManager.db().groups();
        Contacts allContacts = applicationManager.db().contacts();
        ContactData contact = allContacts.iterator().next();
        Groups groupsWithContact = contact.getGroups();
        allGroups.remove(groupsWithContact);
        Groups allGroupsNew = applicationManager.db().groups();
        allGroupsNew.remove(groupsWithContact);
        int id = contact.getId();

        applicationManager.contact().selectContactById(contact.getId());
        applicationManager.contact().addToGroup(allGroupsNew.iterator().next().getId());
        Contacts allContactsAfter = applicationManager.db().contacts();
        ContactData realContact = allContactsAfter.iterator().next().setId(id);
        Groups groupsWithContactAfter = realContact.setId(id).getGroups();

        Assert.assertEquals(groupsWithContactAfter.size(), groupsWithContact.size() + 1);
        Assert.assertEquals(groupsWithContactAfter, groupsWithContact.withAdded(allGroupsNew.iterator().next()));


    }
}
