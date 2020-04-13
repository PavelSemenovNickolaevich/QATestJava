package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GroupModificationUpdateTest extends TestBase {
    private Properties properties;


    @BeforeMethod
    public void ensurePreconditions () throws IOException {
        if (applicationManager.db().groups().size() == 0) {
            applicationManager.goTo().groupPage();
            applicationManager.group()
                    .create(new GroupData()
                            .withName(properties.getProperty("web.nameGroup"))
                            .withHeader(properties.getProperty("web.header"))
                            .withFooter(properties.getProperty("web.footer")));
        }
        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties"))));
        properties = new Properties();
        //     applicationManager.goTo().groupPage();
     /*   if (applicationManager.group().list().size() == 0) {
            applicationManager.group()
                    .create(new GroupData()
                            .withName(properties.getProperty("web.nameGroup"))
                            .withHeader(properties.getProperty("web.header"))
                            .withFooter(properties.getProperty("web.footer")));
        }*/
    }

    @Test
    public void testGroupUpdate () throws IOException {
        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties"))));
        //  Groups before = applicationManager.group().all();
        Groups before = applicationManager.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        // int index = before.size() - 1;
        GroupData group = new GroupData().withId(modifiedGroup.getId())
                .withName(properties.getProperty("web.nameGroup"))
                .withHeader(properties.getProperty("web.header"))
                .withFooter(properties.getProperty("web.footer"));
        // int before  = applicationManager.getGroupHelper().getGroupCount();
        applicationManager.goTo().groupPage();
        applicationManager.group().modify(group);
        //Groups after = applicationManager.group().all();
        Groups after = applicationManager.db().groups();
        // int after  = applicationManager.getGroupHelper().getGroupCount();
        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedGroup);
        before.add(group);
    /*    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);*/
        Assert.assertEquals(before, after);
        MatcherAssert.assertThat(after, CoreMatchers.equalTo((before.without(modifiedGroup).withAdded(group))));
        //  Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
        verifyGroupListInUI();
    }
}
