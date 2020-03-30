package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTest extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsFromXMml () throws IOException {
        //    List<Object[]> list = new ArrayList<Object[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")))) {
            String xml = "";
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                //   String[] split =line.split(";");
                //    list.add(new Object[] {new GroupData(). withName(split[0]).withHeader(split[1]).withFooter(split[2])});
                line = reader.readLine();
            }
            //  list.add(new Object[] {"test1", "header 1", "footer 1"});
            //  list.add(new Object[] {"test2", "header 2", "footer 2"});
            //   list.add(new Object[] {"test3", "header 3", "footer 3"});
            XStream xstream = new XStream();
            xstream.processAnnotations(GroupData.class);
            List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
            return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
            //  return list.iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson () throws IOException {
        //    List<Object[]> list = new ArrayList<Object[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                //   String[] split =line.split(";");
                //    list.add(new Object[] {new GroupData(). withName(split[0]).withHeader(split[1]).withFooter(split[2])});
                line = reader.readLine();
            }
            //  list.add(new Object[] {"test1", "header 1", "footer 1"});
            //  list.add(new Object[] {"test2", "header 2", "footer 2"});
            //   list.add(new Object[] {"test3", "header 3", "footer 3"});
            Gson gson = new Gson();
            List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
            }.getType());
            return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
            //  return list.iterator();
        }
    }

    @Test(dataProvider = "validGroupsFromJson")
    public void testGroupCreation (GroupData group) throws Exception {
        applicationManager.goTo().groupPage();
        //    GroupData group = new GroupData().withName(name).withHeader(header).withFooter(footer);
        // Groups before = applicationManager.group().all();
        Groups before = applicationManager.db().groups();
        //  int before  = applicationManager.getGroupHelper().getGroupCount();

        applicationManager.group().create(group);
        //  Groups after = applicationManager.group().all();
        Groups after = applicationManager.db().groups();
        //  int after  = applicationManager.getGroupHelper().getGroupCount();
        // Assert.assertEquals(after.size(), before.size() + 1);
        assertThat(after.size(), equalTo(before.size() + 1));
   /*     int max = 0;
        for (GroupData g : after) {
            if (g.getId() > max) {
                max = g.getId();
            }
        }

    */
        //  group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
       /* Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);*/
        //  group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt());
        // before.add(group);
        // Assert.assertEquals(before, after);
        //    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
        //   applicationManager.group().logoutGroup();
    }

}