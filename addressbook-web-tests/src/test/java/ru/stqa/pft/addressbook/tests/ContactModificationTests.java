package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by Olga on 01.03.2016.
 */
public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoHome();
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoAddNewPage();
            app.getContactHelper().createContact(new ContactData("Ivan", "Ivanov", "Noosphere", "Shevchenko, 59", "56-373-22-89", "50-362-85-96", "test1"), true);
        }
        app.getNavigationHelper().gotoHome();
        app.getContactHelper().selectContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Petr", "Ivanov", "Idea", null, null, null, null), false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoHome();
    }
}
