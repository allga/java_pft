package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Olga on 24.03.2016.
 */
public class ViewingContactDataTests extends TestBase {

    @Test (enabled = false)
    public void testContactAddress() {
        app.getNavigationHelper().gotoHomePage();
        ContactData contact = app.getContactHelper().getAllContacts().iterator().next();
        ContactData contactInfoFromEditForm = app.getContactHelper().infoFromEditForm(contact);

        assertThat(cleanedAddress(contact.getAddress()), equalTo(cleanedAddress(contactInfoFromEditForm.getAddress())));

    }

    public static String cleanedAddress(String address) {
        return address.replaceAll("\\s+", "").replaceAll("\n", " ");
    }
}
