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
        app.getContactHelper().selectContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Petr", "Petrov", "Idea", "Lenina, 1", "056-744-22-22", "050-340-88-88"));
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoHome();
    }
}
