package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class ChangePasswordTest extends  TestBase {
    @BeforeMethod
    public void startMailServer () {
        applicationManager.mail().start();
    }

    @Test
    public void changePassword() throws IOException, MessagingException {
        String admin = "administrator";
        String passwordAdmin = "root";
        applicationManager.changePassword().login(admin, passwordAdmin);
        applicationManager.changePassword().manageUser();
        List<UserData> list = applicationManager.changePassword().getUsersListWithoutAdmin();
        String email = list.iterator().next().getEmail();
        List<MailMessage> mailMessages = applicationManager.mail().waitForMail(1, 6000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        applicationManager.changePassword().finish(confirmationLink, "test");


    }


    private String findConfirmationLink (List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }


    @AfterMethod(alwaysRun = true)
    public void stopMailServer () {
        applicationManager.mail().stop();
    }
}
