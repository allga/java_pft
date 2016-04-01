package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroups() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.csv")));
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            list.add(new Object[] {new GroupData().setName(split[0]).setHeader(split[1]).setFooter(split[2])});
            line = reader.readLine();
        }
        return list.iterator();
    }

    @Test(dataProvider = "validGroups")
    public void testGroupCreation(GroupData group) {
        app.getNavigationHelper().gotoGroupPage();
        Groups before = app.getGroupHelper().getAllGroups();
        app.getGroupHelper().createGroup(group);
        assertThat(app.getGroupHelper().getGroupCount(), equalTo(before.size() + 1));
        Groups after = app.getGroupHelper().getAllGroups();

        //получаем из потока список идентификаторов, ищем максимальный и преобразуем его в int, потом сравниваем копии множеств
        assertThat(after, equalTo(
                before.withAdded(group.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

    // тест на проверку создания "неправильной" группы
    @Test (enabled = false)
    public void testBadGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        Groups before = app.getGroupHelper().getAllGroups();
        GroupData group = new GroupData().setName("test1'");
        app.getGroupHelper().createGroup(group);
        // хеширование - быстрая проверка кол-ва групп до и после создания, помогает ускорить падение теста
        assertThat(app.getGroupHelper().getGroupCount(), equalTo(before.size()));
        Groups after = app.getGroupHelper().getAllGroups();

        assertThat(after, equalTo(before));
    }
}
