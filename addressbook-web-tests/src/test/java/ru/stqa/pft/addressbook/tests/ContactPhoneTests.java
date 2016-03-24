package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

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
        System.out.println(contact);
        ContactData contactInfoFromEditForm = app.getContactHelper().infoFromEditForm(contact);
        System.out.println(contactInfoFromEditForm.toString());

        assertThat(contact.getHomephone(), equalTo(cleanedPhone(contactInfoFromEditForm.getHomephone())));
        assertThat(contact.getMobilephone(), equalTo(cleanedPhone(contactInfoFromEditForm.getMobilephone())));
        assertThat(contact.getWorkphone(), equalTo(cleanedPhone(contactInfoFromEditForm.getWorkphone())));
    }

    public String cleanedPhone(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}
