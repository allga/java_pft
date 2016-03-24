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
public class ContactPhoneTests extends TestBase {

    @Test
    public void TestContactPhones() {
        app.getNavigationHelper().gotoHomePage();
        ContactData contact = app.getContactHelper().getAllContacts().iterator().next();
        ContactData contactInfoFromEditForm = app.getContactHelper().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }

    private String mergePhones(ContactData contact) {
        return  Arrays.asList(contact.getHomephone(), contact.getMobilephone(), contact.getWorkphone()).
                stream().filter((s) -> ! s.equals("")).
                map(ContactPhoneTests::cleanedPhone).
                collect(Collectors.joining("\n"));
    }

    public static String cleanedPhone(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}
