package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test (enabled = true)
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoHome();
    Contacts before = app.getContactHelper().getAllContacts();
    ContactData contact = new ContactData().
            setFirstname("Leonid").setLastname("Ivanov").setCompany("Noosphere").setAddress("Shevchenko, 59").
            setHomephone("56-373-22-89").setMobilephone("50-362-85-96").setGroup("test1");
    app.getContactHelper().createContact(contact, true);
    Contacts after = app.getContactHelper().getAllContacts();
    assertThat(after.size(), equalTo(before.size() + 1));

    assertThat(after, equalTo(
            before.withAdded(contact.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }



}
