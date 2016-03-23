package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupCreationTests extends TestBase {

    @Test (enabled = true)
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        Groups before = app.getGroupHelper().getAllGroups();
        GroupData group = new GroupData().setName("test1").setHeader("test2").setFooter("test3");
        app.getGroupHelper().createGroup(group);
        Groups after = app.getGroupHelper().getAllGroups();
        assertThat(after.size(), equalTo(before.size() + 1));

        //получаем из потока список идентификаторов, ищем максимальный и преобразуем его в int, потом сравниваем копии множеств
        assertThat(after, equalTo(
                before.withAdded(group.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

}
