package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

/**
 * Created by Olga on 01.03.2016.
 */
public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.getNavigationHelper().gotoGroupPage();
        if (app.getGroupHelper().getAllGroups().size() == 0) {
            app.getGroupHelper().createGroup(new GroupData().setName("test1").setHeader("test2").setFooter("test3"));
        }
    }

    @Test (enabled = true)
    public void testGroupModification() {
        Set<GroupData> before = app.getGroupHelper().getAllGroups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().setId(modifiedGroup.getId()).setName("test1111");
        app.getGroupHelper().modifyGroup(group);
        Set<GroupData> after = app.getGroupHelper().getAllGroups();
        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedGroup);
        before.add(group);
        Assert.assertEquals(before, after);
    }

}
