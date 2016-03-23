package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.*;

public class ContactCreationTests extends TestBase {

  @Test (enabled = true)
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoHome();
    Set<ContactData> before = app.getContactHelper().getAllContacts();
    ContactData contact = new ContactData().
            setFirstname("Leonid").setLastname("Ivanov").setCompany("Noosphere").setAddress("Shevchenko, 59").
            setHomephone("56-373-22-89").setMobilephone("50-362-85-96").setGroup("test1");
    app.getContactHelper().createContact(contact, true);
    Set<ContactData> after = app.getContactHelper().getAllContacts();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }



}
