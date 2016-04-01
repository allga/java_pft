package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        //извлекаем список объектов ContactData, это можно сделать через TypeToken, использовать List<ContactData>.class нельзя
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
        // каждый объект помещаем в массив из 1го эл-та для тестов (TestNG)
        return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) throws Exception {
        app.getNavigationHelper().gotoHomePage();
        Contacts before = app.getContactHelper().getAllContacts();

        app.getContactHelper().createContact(contact, true);

        Contacts after = app.getContactHelper().getAllContacts();
        assertThat(after.size(), equalTo(before.size() + 1));

        assertThat(after, equalTo(
                before.withAdded(contact.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

    @Test(enabled = false)
    public void testContactCreationWithData() throws Exception {
        app.getNavigationHelper().gotoHomePage();
        Contacts before = app.getContactHelper().getAllContacts();
        File photo = new File("src/test/resources/photo0.jpg");
        ContactData contact = new ContactData().
                setFirstname("Leonid").setLastname("Ivanov").setCompany("Noosphere").setAddress("Shevchenko, 59").
                setHomephone("56-373-22-89").setMobilephone("50-362-85-96").setPhoto(photo).setGroup("test1");
        app.getContactHelper().createContact(contact, true);
        Contacts after = app.getContactHelper().getAllContacts();
        assertThat(after.size(), equalTo(before.size() + 1));

        assertThat(after, equalTo(
                before.withAdded(contact.setId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

    @Test(enabled = false)
    public void testContactMultyCreation() throws Exception {
        for (int i = 0; i < 100; i++) {
            app.getNavigationHelper().gotoHomePage();
            ContactData contact = new ContactData().
                    setFirstname("Semen" + i).setLastname("Ivanov" + i).setCompany("Noosphere").setAddress("Shevchenko, 59").
                    setHomephone("56-373-22-89").setMobilephone("50-362-85-96").setGroup("test1");
            app.getContactHelper().createContact(contact, true);

        }
    }
}
