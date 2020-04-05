package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {
    @DataProvider
    public Iterator<Object[]> validContactsFromJson () throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
        }.getType());
        return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validContactsFromJson")
    public void testCreateNewContact (ContactData contact) throws Exception {
        applicationManager.goTo().goToHome();
        //  Contacts before = applicationManager.contact().all();
        Contacts before = applicationManager.db().contacts();
        applicationManager.goTo().goToAddNewContact();
        // List<ContactData> before = applicationManager.contact().getContactList();
        //  Contacts before = applicationManager.contact().getContactList();
        // Set<ContactData> before = applicationManager.contact().all();
        //   Contacts before = applicationManager.contact().all();
        //ContactData contact = new ContactData("Pavel111", "First", "Ivanov"
        //         , "skynet", "Moscow 3-builder street 10", "111", "222",
        //         "333", "123@gmail.com", "ivanov@mail.com"  );
        //   int before = applicationManager.getContactHelper().getContactCount();   //Счетчик контактов до
        applicationManager.contact().createNew(contact);
        // List<ContactData> after = applicationManager.contact().getContactList();
        //  Contacts after = applicationManager.contact().getContactList();
        //   Set<ContactData> after = applicationManager.contact().all();
        // Contacts after = applicationManager.contact().all();
        Contacts after = applicationManager.db().contacts();
        //   int after = applicationManager.getContactHelper().getContactCount();   //Счетчик контактов после
        //  Assert.assertEquals(after.size(), before.size() + 1);//Сверка счетчиков
        //     assertThat(after.size(), equalTo(before.size() + 1));

    /*    int max = 0;
        for (ContactData g: after) {
            if (g.getId() > max) {
                max = g.getId();
            }
        }*/
        //   contact.setId(max);
        // assertThat(after, equalTo(
        //        before.withAdded(contact.setId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

    //    assertThat(after, equalTo(
     //   before.withAdded(contact.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
        assertThat(after, equalTo(
                before.withAdded(contact.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
      //  contact.setId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        //    before.add(contact);
        //    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        //   before.sort(byId);
        //    after.sort(byId);
        // Assert.assertEquals(before, after);
        //    assertThat(after, equalTo(before.withAdded(contact)));
        //    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
        //   applicationManager.contact().logoutContact();
    }


}