package ru.stqa.pft.addressbook.rf;

import org.openqa.selenium.remote.BrowserType;
import ru.stqa.pft.addressbook.appmanger.ApplicationManager;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.IOException;

public class AddressbookKeywords {

    private ApplicationManager app;


    public void inintApplicationManager() throws IOException {
        app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
        app.init();
    }

    public void stopApplicationManager() {
        app.stop();
        app = null;
    }

    public int getGroupCount() {
        app.goTo().groupPage();
        return app.group().getGroupCount();
    }

    public void createGroup(String name, String header, String footer) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName(name).withHeader(header).withFooter(footer));
    }
}
