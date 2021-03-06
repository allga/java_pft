package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Olga on 01.03.2016.
 */
public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        if (app.getDbHelper().groups().size() == 0) {
            app.getNavigationHelper().gotoGroupPage();
            app.getGroupHelper().createGroup(new GroupData().setName("test1").setHeader("test2").setFooter("test3"));
        }
    }

    @Test (enabled = true)
    public void testGroupModification() {
        Groups before = app.getDbHelper().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().setId(modifiedGroup.getId()).setName("test222").
                setHeader("header222").setFooter("footer222");
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().modifyGroup(group);

        assertThat(app.getGroupHelper().getGroupCount(), equalTo(before.size()));

        Groups after = app.getDbHelper().groups();

        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));

        verifyGroupListInUI();
    }
}
