package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ContactModificationUpdateTest extends TestBase {
    private Properties properties;

    @BeforeMethod
    public void ensurePreconditionals () throws IOException {
      //  applicationManager.goTo().goToHome();
        if (applicationManager.contact().all().size() == 0) {
            applicationManager.contact()
                    .createContact(new ContactData(properties.getProperty("web.firstname")
                            , properties.getProperty("web.lastname"), properties.getProperty("web.middlename")
                            , properties.getProperty("web.company"), properties.getProperty("web.address")
                            , properties.getProperty("web.phoneHome"), properties.getProperty("web.phoneMobile")
                            , properties.getProperty("web.phoneWork"), properties.getProperty("web.emailOne")
                            , properties.getProperty("web.emailTwo")));
        }
        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties"))));
    }

    @Test
    public void testContactUpdate () throws IOException {
        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties"))));
        //   Contacts before = applicationManager.contact().all();
        Contacts before = applicationManager.db().contacts();
        //   int index = before.size() - 1;
        ContactData modifyContact = before.iterator().next();

        //List<ContactData> before = applicationManager.contact().getContactList();
        // int index = before.size()-1;
        ContactData contact = new ContactData(modifyContact.getId(), properties.getProperty("web.firstname")
                , properties.getProperty("web.lastname"), properties.getProperty("web.middlename")
                , properties.getProperty("web.company"), properties.getProperty("web.address")
                , properties.getProperty("web.phoneHome"), properties.getProperty("web.phoneMobile")
                , properties.getProperty("web.phoneWork"), properties.getProperty("web.emailOne")
                , properties.getProperty("web.emailTwo"));
    //    applicationManager.goTo().goToAddNewContact();
        //   int before = applicationManager.getContactHelper().getContactCount();  //Счетчик контактов до
        // applicationManager.contact().modifyContact(contact, index);
        applicationManager.contact().modifyContactNew(contact);
        //    List<ContactData> after = applicationManager.contact().getContactList();
        //   Set<ContactData> after = applicationManager.contact().all();
        applicationManager.goTo().goToAddNewContact();
        Contacts after = applicationManager.db().contacts();
        //  Contacts after = applicationManager.contact().all();
        //   int after = applicationManager.getContactHelper().getContactCount();  //Счетчик контактов после
        //    Assert.assertEquals(after.size(), before.size());  //Сверка контактов
        //   before.remove(modifyContact);
        //  before.add(contact);
        //  Assert.assertEquals(before, after);
        MatcherAssert.assertThat(after, CoreMatchers.equalTo((before.without(modifyContact).withAdded(contact))));
        //  Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
       //   applicationManager.contact().logoutContact();
        verifyContactListUI();
    }

}


