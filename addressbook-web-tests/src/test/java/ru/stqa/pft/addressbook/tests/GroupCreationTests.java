package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class GroupCreationTests extends TestBase {

    @Test (enabled = true)
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        Set<GroupData> before = app.getGroupHelper().getAllGroups();
        GroupData group = new GroupData().setName("test1").setHeader("test2").setFooter("test3");
        app.getGroupHelper().createGroup(group);
        Set<GroupData> after = app.getGroupHelper().getAllGroups();
        Assert.assertEquals(after.size(), before.size() + 1);

        //получаем из потока список идентификаторов, ищем максимальный и преобразуем его в int
        group.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        before.add(group);
        Assert.assertEquals(before, after);
    }

}
