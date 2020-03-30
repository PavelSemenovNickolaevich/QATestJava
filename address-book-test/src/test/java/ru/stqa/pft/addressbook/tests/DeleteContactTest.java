package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.Matchers.equalTo;

public class DeleteContactTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditionals () {
        applicationManager.goTo().goToHome();
        if (applicationManager.contact().all().size() == 0) {
            applicationManager.contact()
                    .createContact(new ContactData("Pavel111", "First", "Ivanov"
                            , "skynet", "Moscow 3-builder street 10", "111", "222",
                            "333", "123@gmail.com", "ivanov@mail.com"));
        }
    }

    @Test
    public void deleteContactTest () {
        // List<ContactData> before = applicationManager.contact().getContactList();
        // Contacts before = applicationManager.contact().all();
        Contacts before = applicationManager.db().contacts();
        ContactData deletedContact = before.iterator().next();
        //  int before = applicationManager.getContactHelper().getContactCount();  //Счетчик контактов до
        //int index = before.size() - 1;
        //   applicationManager.contact().deleteContact(index);
        applicationManager.contact().deleteContact(deletedContact);
        //  List<ContactData> after = applicationManager.contact().getContactList();
        //  Contacts after = applicationManager.contact().all();
        Contacts after = applicationManager.db().contacts();
        //  int after = applicationManager.getContactHelper().getContactCount();  //Счетчик контактов после
        Assert.assertEquals(after.size(), before.size() - 1); // Сверка счетчиков

        //  before.remove(index);
        before.remove(deletedContact);
        Assert.assertEquals(before, after);
        MatcherAssert.assertThat(after, equalTo(before.without(deletedContact)));

    }
}
