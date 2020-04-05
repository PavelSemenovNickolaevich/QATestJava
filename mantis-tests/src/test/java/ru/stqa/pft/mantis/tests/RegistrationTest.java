package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class RegistrationTest extends TestBase {

    //  @BeforeMethod
    public void startMailServer () {
        applicationManager.mail().start();
    }

    @Test
    public void testRegistration () throws IOException, MessagingException {
        long now = System.currentTimeMillis();
        String email = String.format("123%s@gmail.com", now);
        String user = String.format("user%s", now);
        String password = "password";
        applicationManager.james().createUser(user, password);
        applicationManager.registration().start(user, email);
        //   List<MailMessage> mailMessages = applicationManager.mail().waitForMail(2, 10000);
        List<MailMessage> mailMessages = applicationManager.james().waitForMail(user, password, 600000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        applicationManager.registration().finish(confirmationLink, password);
        assertTrue(applicationManager.newSession().login(user, password));

    }

    private String findConfirmationLink (List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    // @AfterMethod(alwaysRun = true)
    public void stopMailServer () {
        applicationManager.mail().stop();
    }
}
