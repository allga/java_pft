package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Olga on 01.03.2016.
 */
public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().gotoHomePage();
        if (app.getContactHelper().getAllContacts().size() == 0) {
            app.getContactHelper().createContact(new ContactData().setFirstname("Ivan").setLastname("Ivanov").
                    setCompany("Noosphere").setAddress("Shevchenko, 59").setHomephone("56-373-22-89").
                    setMobilephone("50-362-85-96")
//                    .setGroup("test1")
                    , true);
        }
    }


    @Test (enabled = true)
    public void testContactModification() {
        Contacts before = app.getDbHelper().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().
                setId(modifiedContact.getId()).setFirstname("Egor").setLastname("Petrov").
                setCompany("Idea").setAddress("Shevchenko, 100").
                setHomephone(modifiedContact.getHomephone()).setMobilephone(modifiedContact.getMobilephone()).
                setWorkphone(modifiedContact.getWorkphone()).
                setEmail1(modifiedContact.getEmail1()).setEmail2(modifiedContact.getEmail2()).
                setEmail3(modifiedContact.getEmail3());
        app.getContactHelper().modifyContact(modifiedContact.getId(), contact);
        Contacts after = app.getDbHelper().contacts();

        System.out.println(after);
        Assert.assertEquals(after.size(), before.size());

        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
