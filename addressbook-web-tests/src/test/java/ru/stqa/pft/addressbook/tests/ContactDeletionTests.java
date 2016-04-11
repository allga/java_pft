package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Olga on 01.03.2016.
 */
public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().gotoHomePage();
        if (app.getContactHelper().getAllContacts().size() == 0) {
            app.getContactHelper().createContact(new ContactData().setFirstname("Ivan").setLastname("Ivanov").
                    setCompany("Noosphere").setAddress("Shevchenko, 59").
                    setHomephone("56-373-22-89").setMobilephone("50-362-85-96").setGroup("test1"), true);
        }
    }

    @Test (enabled = true)
    public void testContactDeletion() {
        Contacts before = app.getDbHelper().contacts();
        ContactData deletedContact = before.iterator().next();
        app.getContactHelper().deleteContact(deletedContact);
        Contacts after = app.getDbHelper().contacts();
        assertThat(after.size(), equalTo(before.size() - 1));

        assertThat(after, equalTo(before.without(deletedContact)));
    }

    @Test (enabled = false)
    public void testAllContactsDeletion() {
        app.getContactHelper().deleteAllContacts();
        Contacts after = app.getContactHelper().getAllContacts();
        assertThat(after.size(), equalTo(0));
    }
}
