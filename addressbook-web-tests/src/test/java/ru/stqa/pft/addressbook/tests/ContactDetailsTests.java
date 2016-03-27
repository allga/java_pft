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
        mergePhones(contactInfoFromEditForm);
        mergeEmails(contactInfoFromEditForm);
        System.out.println(mergeContent(contactInfoFromEditForm));
        ContactData contactInfoFromDetailsPage = app.getContactHelper().infoFromDetailsPage(contact);
        System.out.println(cleaned(contactInfoFromDetailsPage.getAllContent()).toString());

        assertThat(mergeContent(contactInfoFromEditForm), equalTo(cleaned(contactInfoFromDetailsPage.getAllContent())));

    }

    private void mergePhones(ContactData contact) {
        String phone = "";
        if (!contact.getHomephone().equals("")) {
            phone += String.format("H:%s", contact.getHomephone());
        }
        if (!contact.getMobilephone().equals("")) {
            phone += String.format("M:%s", contact.getMobilephone());
        }
        if (!contact.getWorkphone().equals("")) {
            phone += String.format("W:%s", contact.getWorkphone());
        }
        contact.setAllPhones(phone);
    }

    private void mergeEmails(ContactData contact) {
        String[] emails = {contact.getEmail1(), contact.getEmail2(), contact.getEmail3()};
        String emailString = Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3()).
                stream().filter((s) -> ! s.equals("")).map(ViewingContactDataTests::cleaned).
                collect(Collectors.joining("\n"));
        contact.setAllEmails(emailString);
    }

    private String mergeContent(ContactData contact) {
        return Arrays.asList(contact.getFirstname(), contact.getLastname(), contact.getAddress(), contact.getAllPhones(),
                contact.getAllEmails()).
                stream().map(ViewingContactDataTests::cleaned).
                collect(Collectors.joining());
    }

    public static String cleaned(String collectedContact) {
        return collectedContact.replaceAll("\\s+", "").replaceAll("\n", " ");
    }

}
