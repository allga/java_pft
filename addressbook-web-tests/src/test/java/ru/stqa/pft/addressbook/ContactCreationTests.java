package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    gotoAddNewPage();
    fillContactForm(new ContactData("Ivan", "Ivanov", "Noosphere", "Shevchenko, 59", "56-373-22-89", "50-362-85-96"));
    submitContact();
  }

}
