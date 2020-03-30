package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneAddressEmailTest extends TestBase {

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
    public void testContactPhonesEmailAdress () {
        //    applicationManager.goTo().goToAddNewContact();
        ContactData contact = applicationManager.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = applicationManager.contact().infoFromEditForm(contact);

      //  assertThat(contact.getPhoneHome(), CoreMatchers.equalTo(cleaned(contactInfoFromEditForm.getPhoneHome())));
      //  assertThat(contact.getPhoneMobile(), CoreMatchers.equalTo(cleaned(contactInfoFromEditForm.getPhoneMobile())));
      //  assertThat(contact.getPhoneWork(), CoreMatchers.equalTo(cleaned(contactInfoFromEditForm.getPhoneWork())));
        // assertThat(contact.getEmailOne(), CoreMatchers.equalTo(cleaned(contactInfoFromEditForm.getEmailOne())));
        //  assertThat(contact.getEmailTwo(), CoreMatchers.equalTo(cleaned(contactInfoFromEditForm.getEmailTwo())));
        //  assertThat(contact.getAdress(), CoreMatchers.equalTo(cleaned(contactInfoFromEditForm.getAdress())));
        assertThat(contact.getAllEmails(), CoreMatchers.equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAllPhones(), CoreMatchers.equalTo(mergePnones(contactInfoFromEditForm)));
        assertThat(contact.getAdress(), CoreMatchers.equalTo(mergeAdress(contactInfoFromEditForm)));

    }

    private String mergePnones (ContactData contact) {
        return Arrays.asList(contact.getPhoneHome(), contact.getPhoneMobile(), contact.getPhoneWork())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneAddressEmailTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    private String mergeEmails (ContactData contact) {
        return Arrays.asList(contact.getEmailOne(), contact.getEmailTwo())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneAddressEmailTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    private String mergeAdress (ContactData contact) {
        return Arrays.asList(contact.getAdress())
                .stream().filter((s) -> !s.equals("")).collect(Collectors.joining(""));

    }

    public static String cleaned (String phone) {
        //очищение строчек
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}