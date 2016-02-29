package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.gotoAddNewPage();
    app.fillContactForm(new ContactData("Ivan", "Ivanov", "Noosphere", "Shevchenko, 59", "56-373-22-89", "50-362-85-96"));
    app.submitContact();
  }

}