package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Olga on 27.03.2016.
 */
public class ContactDetailsTests extends TestBase {

    @Test
    public void testContactDetails() {
        app.getNavigationHelper().gotoHomePage();
        ContactData contact = app.getContactHelper().getAllContacts().iterator().next();
        ContactData contactInfoFromEditForm = app.getContactHelper().infoFromEditForm(contact);
        app.getContactHelper().mergePhones(contactInfoFromEditForm);
        app.getContactHelper().mergeEmails(contactInfoFromEditForm);
        ContactData contactInfoFromDetailsPage = app.getContactHelper().infoFromDetailsPage(contact);

        assertThat(mergeContent(contactInfoFromEditForm), equalTo(cleaned(contactInfoFromDetailsPage.getAllContent())));
    }

    private String mergeContent(ContactData contact) {
        return Arrays.asList(contact.getFirstname(), contact.getLastname(), contact.getCompany(), contact.getAddress(), contact.getAllPhones(),
                contact.getAllEmails()).
                stream().map(ViewingContactDataTests::cleaned).
                collect(Collectors.joining());
    }

    public static String cleaned(String collectedContact) {
        return collectedContact.replaceAll("\\s+", "").replaceAll("\n", " ");
    }
}
