package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoHome();
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("Leonid", "Ivanov", "Noosphere", "Shevchenko, 59", "56-373-22-89", "50-362-85-96", "test1");
    app.getContactHelper().createContact(contact, true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    before.get(before.size() - 1).setId(after.get(before.size() - 1).getId());
    Assert.assertEquals(before, after);
  }



}
