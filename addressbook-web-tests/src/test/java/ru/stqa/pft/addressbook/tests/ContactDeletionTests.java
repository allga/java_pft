package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

/**
 * Created by Olga on 01.03.2016.
 */
public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().gotoHome();
        if (app.getContactHelper().getAllContacts().size() == 0) {
            app.getContactHelper().createContact(new ContactData().setFirstname("Ivan").setLastname("Ivanov").
                    setCompany("Noosphere").setAddress("Shevchenko, 59").
                    setHomephone("56-373-22-89").setMobilephone("50-362-85-96").setGroup("test1"), true);
        }
    }

    @Test (enabled = true)
    public void testContactDeletion() {
        Set<ContactData> before = app.getContactHelper().getAllContacts();
        ContactData deletedContact = before.iterator().next();
        app.getContactHelper().deleteContact(deletedContact);
        Set<ContactData> after = app.getContactHelper().getAllContacts();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedContact);
        Assert.assertEquals(after, before);
    }

}
