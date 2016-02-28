package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class GroupDeletionTests extends TestBase {

    @Test
    public void testGroupDeletion() {
//        wd.findElement(By.name("searchstring")).click();
//        wd.findElement(By.name("searchstring")).sendKeys("\\9");
        gotoGroupPage();
        selectGroup();
        deleteSelectedGroups();
        returnToGroupPage();
    }

}
