package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

/**
 * Created by Olga on 01.03.2016.
 */
public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoHome();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().submitContactDeletion();
        app.getNavigationHelper().gotoHome();
    }
}
