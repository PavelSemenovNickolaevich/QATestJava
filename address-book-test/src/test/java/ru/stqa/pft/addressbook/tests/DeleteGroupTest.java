package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class DeleteGroupTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditions () {
        applicationManager.goTo().groupPage();
        if (applicationManager.group().list().size() == 0) {
            applicationManager.group()
                    .create(new GroupData().withName("qaNAme").withHeader("QaHeader").withFooter("qaFooter"));
        }
    }

    @Test
    public void deleteGroup () {
        Groups before = applicationManager.db().groups();
        GroupData deleteGroup = before.iterator().next();
        //  int before  = applicationManager.getGroupHelper().getGroupCount();
        applicationManager.group().delete(deleteGroup);
        Groups after = applicationManager.db().groups();
        // int after  = applicationManager.getGroupHelper().getGroupCount();
        assertEquals(after.size(), before.size() - 1);

        //   before.remove(deleteGroup);
        assertThat(after, equalTo(before.without(deleteGroup)));
        //    Assert.assertEquals(before , after);
    }

}
