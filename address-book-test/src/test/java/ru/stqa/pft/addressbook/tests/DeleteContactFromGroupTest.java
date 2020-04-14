package ru.stqa.pft.addressbook.tests;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        ContactData contactSelect ;
        GroupData groupSelect = null;
        ContactData contactsAfter = null;
        //      int id = contactSelect.getId();

        Groups allGroups = applicationManager.db().groups();
        Contacts allContacts = applicationManager.db().contacts();
        contactSelect = allContacts.iterator().next();

        for (ContactData contact : allContacts) {
            Groups groupContact  = contact.getGroups();
            if (groupContact.size() > 0 ) {
                contactSelect = contact;
                groupSelect = contact.getGroups().iterator().next();
                break;
            }
        }

        if(contactSelect.getGroups().size() == 0) {
            groupSelect = allGroups.iterator().next();
            applicationManager.contact().selectGroup(groupSelect);
        }


        applicationManager.contact().selectGroup(groupSelect);
        applicationManager.contact().selectContactById(contactSelect.getId());
        applicationManager.contact().removeContactFromGroup();
        applicationManager.goTo().goToHome();

        Contacts contactsAfterAll = applicationManager.db().contacts();
        for (ContactData contactsAfterRemove: contactsAfterAll) {
            if (contactsAfterRemove.getId() == contactSelect.getId()) {
                contactsAfter = contactsAfterRemove;
            }
        }
        assertThat(contactsAfter.getGroups(), equalTo(contactsAfter.getGroups().withAdded(groupSelect)));






    }

}
