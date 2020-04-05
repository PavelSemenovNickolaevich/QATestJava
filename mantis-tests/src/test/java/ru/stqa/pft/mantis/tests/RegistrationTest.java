package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;

public class RegistrationTest extends TestBase {

    @Test
    public void testRegistration() {
        applicationManager.registration().start("user1", "123@gmail.com");
    }
}
