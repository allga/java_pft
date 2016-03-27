package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by Olga on 27.03.2016.
 */
public class ContactDetailsTests extends TestBase {

    @Test
    public void testContactDetails() {
        app.getNavigationHelper().gotoHomePage();
        ContactData contact = app.getContactHelper().getAllContacts().iterator().next();
        ContactData contactInfoFromEditForm = app.getContactHelper().infoFromEditForm(contact);
        ContactData contactInfoFromDetailsPage = app.getContactHelper().infoFromDetailsPage(contact);
    }
}
