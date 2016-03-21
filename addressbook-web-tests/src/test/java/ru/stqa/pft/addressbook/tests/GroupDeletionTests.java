package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;
import java.util.Set;

public class GroupDeletionTests extends TestBase {


    @BeforeMethod
    public void ensurePreconditions(){
        app.getNavigationHelper().gotoGroupPage();
        if (app.getGroupHelper().getAllGroups().size() == 0) {
            app.getGroupHelper().createGroup(new GroupData().setName("test1").setHeader("test2").setFooter("test3"));
        }
    }


    @Test (enabled = true)
    public void testGroupDeletion() {
        Set<GroupData> before = app.getGroupHelper().getAllGroups();
        GroupData deletedGroup = before.iterator().next();
        app.getGroupHelper().deleteGroup(deletedGroup);
        Set<GroupData> after = app.getGroupHelper().getAllGroups();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedGroup);
        Assert.assertEquals(before, after);
    }

}
