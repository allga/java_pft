package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

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

        assertThat(cleaned(contact.getAddress()), equalTo(cleaned(contactInfoFromEditForm.getAddress())));

    }

    @Test
    public void testContactEmail() {
        app.getNavigationHelper().gotoHomePage();
        ContactData contact = app.getContactHelper().getAllContacts().iterator().next();
        ContactData contactInfoFromEditForm = app.getContactHelper().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3()).
                stream().filter((s) -> ! s.equals("")).map(ViewingContactDataTests::cleaned).
                collect(Collectors.joining("\n"));
    }

    public static String cleaned(String address) {
        return address.replaceAll("\\s+", "").replaceAll("\n", " ");
    }
}
