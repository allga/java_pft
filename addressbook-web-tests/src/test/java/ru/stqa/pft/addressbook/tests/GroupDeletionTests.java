package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
        Groups before = app.getGroupHelper().getAllGroups();
        GroupData deletedGroup = before.iterator().next();
        app.getGroupHelper().deleteGroup(deletedGroup);
        assertThat(app.getGroupHelper().getGroupCount(), equalTo(before.size() - 1));
        Groups after = app.getGroupHelper().getAllGroups();

        assertThat(after, equalTo(before.without(deletedGroup)));
    }

}
