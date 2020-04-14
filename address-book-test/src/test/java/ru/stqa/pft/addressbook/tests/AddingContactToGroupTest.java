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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        ContactData contactSelect = null;
        GroupData groupSelect = null;
        ContactData contactsAfter = null;
  //      int id = contactSelect.getId();

        Groups allGroups = applicationManager.db().groups();
        Contacts allContacts = applicationManager.db().contacts();
        for (ContactData contact : allContacts) {
            Groups groupContact  = contact.getGroups();
            if (groupContact.size() != allGroups.size()) {
                allGroups.removeAll(groupContact);
                groupSelect = allGroups.iterator().next();
                contactSelect = contact;
                break;
            }
        }

        if (allContacts.size() == 0 ) {
            ContactData contact = new ContactData("Pavel111", "First", "Ivanov"
                            , "skynet", "Moscow 3-builder street 10", "111", "222",
                         "333", "123@gmail.com", "ivanov@mail.com");
            applicationManager.contact().createContact(contact);
            Contacts contactNew = applicationManager.db().contacts();
            contact.setId(contactNew.stream().mapToInt((g) -> (g).getId()).max().getAsInt());
            ContactData addContact = contact;
            ContactData contactNewTwo = allContacts.iterator().next();
        }


       // applicationManager.contact().selectContactById(contactSelect.getId());
        applicationManager.contact().groupsInPage();
        applicationManager.contact().selectGroup(contactSelect, groupSelect);
    //    applicationManager.contact().addToGroup(allGroupsNew.iterator().next().getId());
        Contacts allContactsAfter = applicationManager.db().contacts();
        for (ContactData contactAfter: allContactsAfter) {
            if (contactAfter.getId() == contactSelect.getId()) { contactsAfter  = contactAfter;

            }
        }

        assertThat(contactSelect.getGroups(), equalTo(contactsAfter.getGroups().without(groupSelect)));


    }
}
