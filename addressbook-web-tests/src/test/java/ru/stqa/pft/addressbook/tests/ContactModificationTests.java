package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Olga on 01.03.2016.
 */
public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().gotoHome();
        if (app.getContactHelper().getAllContacts().size() == 0) {
            app.getContactHelper().createContact(new ContactData().setFirstname("Ivan").setLastname("Ivanov").
                    setCompany("Noosphere").setAddress("Shevchenko, 59").setHomephone("56-373-22-89").
                    setMobilephone("50-362-85-96").setGroup("test1"), true);
        }
    }


    @Test (enabled = true)
    public void testContactModification() {
        Set<ContactData> before = app.getContactHelper().getAllContacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().setId(modifiedContact.getId()).setFirstname("Nick").setLastname("Petrov").
                setCompany("Idea").setAddress("Shevchenko, 100");
        app.getContactHelper().modifyContact(modifiedContact.getId(), contact);
        Set<ContactData> after = app.getContactHelper().getAllContacts();
        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedContact);
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
