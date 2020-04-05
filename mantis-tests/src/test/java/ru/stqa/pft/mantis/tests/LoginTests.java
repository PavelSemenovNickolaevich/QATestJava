package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanger.HttpSession;

import java.io.IOException;

import static org.testng.Assert.*;

public class LoginTests extends TestBase {

    @Test
    public void testLogin () throws IOException {
        HttpSession session = applicationManager.newSession();
        assertTrue(session.login("administrator", "root"));
        assertTrue(session.isLoggedInAs("administrator"));
    }
}
